package org.collabtask.task.core.service.usecases;

import org.collabtask.helpers.ZonedDateTimeHelper;
import org.collabtask.task.core.contracts.ITaskRepository;
import org.collabtask.task.core.dto.TaskClient;
import org.collabtask.task.core.dto.UpdateTask;
import org.collabtask.task.core.exception.InvalidTaskException;
import org.collabtask.task.core.exception.TaskNotFoundException;
import org.collabtask.task.core.model.TaskStatus;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TaskUpdater {
    ITaskRepository taskRepository;

    public TaskUpdater(ITaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Uni<TaskClient> update(String id, UpdateTask updateTask) throws TaskNotFoundException, InvalidTaskException {
        return taskRepository.findById(id)
                .flatMap(oldTask -> {
                    // Validate status transitions
                    if (oldTask.getStatus() == TaskStatus.COMPLETED && updateTask.getStatus() != null) {
                        return Uni.createFrom()
                                .failure(() -> new InvalidTaskException("Cannot update status COMPLETED"));
                    }

                    if (updateTask.getStatus() == TaskStatus.OVERDUE && updateTask.getDueDate() == null) {
                        return Uni.createFrom()
                                .failure(() -> new InvalidTaskException(
                                        "Cannot update status to OVERDUE without due date"));
                    }

                    if (oldTask.getStatus() == TaskStatus.TO_DO && updateTask.getStatus() == TaskStatus.OVERDUE) {
                        return Uni.createFrom()
                                .failure(() -> new InvalidTaskException("Cannot update status from TO_DO to OVERDUE"));
                    }

                    if (oldTask.getStatus() == TaskStatus.IN_PROGRESS
                            && updateTask.getStatus() == TaskStatus.OVERDUE) {
                        return Uni.createFrom()
                                .failure(() -> new InvalidTaskException(
                                        "Cannot update status from IN_PROGRESS to OVERDUE"));
                    }

                    if (oldTask.getStatus() == TaskStatus.IN_PROGRESS
                            && updateTask.getStatus() == TaskStatus.TO_DO) {
                        return Uni.createFrom()
                                .failure(() -> new InvalidTaskException(
                                        "Cannot update status from IN_PROGRESS to TO_DO"));
                    }

                    if (oldTask.getStatus() == TaskStatus.BLOCKED && updateTask.getStatus() == TaskStatus.OVERDUE) {
                        return Uni.createFrom()
                                .failure(
                                        () -> new InvalidTaskException("Cannot update status from BLOCKED to OVERDUE"));
                    }

                    if (oldTask.getStatus() == TaskStatus.BLOCKED
                            && updateTask.getStatus() == TaskStatus.COMPLETED) {
                        return Uni.createFrom()
                                .failure(() -> new InvalidTaskException(
                                        "Cannot update status from BLOCKED to COMPLETED"));
                    }

                    // Update overdue status if due date has passed
                    if (updateTask.getDueDate() != null
                            && updateTask.getDueDate().isBefore(ZonedDateTimeHelper.now())) {
                        if (oldTask.getStatus() != TaskStatus.COMPLETED) {
                            updateTask.setStatus(TaskStatus.OVERDUE);
                        } else {
                            return Uni.createFrom()
                                    .failure(() -> new InvalidTaskException("Cannot update status from COMPLETED"));
                        }
                    }

                    // Update status to TO_DO or IN_PROGRESS if due date is in the future
                    if (oldTask.getStatus() == TaskStatus.OVERDUE && updateTask.getDueDate() != null
                            && updateTask.getDueDate().isAfter(ZonedDateTimeHelper.now())) {
                        // TODO: "TO_DO" ou "IN_PROGRESS" selon l’état précédent avant OVERDUE
                        oldTask.setStatus(TaskStatus.IN_PROGRESS);
                    }

                    return taskRepository.update(id, updateTask);
                });
    }
}
