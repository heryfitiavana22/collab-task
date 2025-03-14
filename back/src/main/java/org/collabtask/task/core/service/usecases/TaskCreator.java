package org.collabtask.task.core.service.usecases;

import java.util.List;

import org.collabtask.helpers.ZonedDateTimeHelper;
import org.collabtask.task.core.dto.CreateTask;
import org.collabtask.task.core.dto.TaskClient;
import org.collabtask.task.core.exception.InvalidTaskException;
import org.collabtask.task.core.model.TaskStatus;
import org.collabtask.task.database.TaskEntityRepository;
import org.collabtask.user.core.dto.UserClient;
import org.collabtask.user.core.exception.UserNotFoundException;
import org.collabtask.user.database.UserEntityRepository;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TaskCreator {
    TaskEntityRepository taskEntityRepository;

    UserEntityRepository userEntityRepository;

    public TaskCreator(TaskEntityRepository taskEntityRepository, UserEntityRepository userEntityRepository) {
        this.taskEntityRepository = taskEntityRepository;
        this.userEntityRepository = userEntityRepository;
    }

    public Uni<TaskClient> create(CreateTask createTask) throws InvalidTaskException, UserNotFoundException {
        if (createTask.getTitle() == null || createTask.getTitle().isEmpty()) {
            return Uni.createFrom().failure(() -> new InvalidTaskException("Title is required"));
        }
        if (createTask.getPriority() == null) {
            return Uni.createFrom().failure(() -> new InvalidTaskException("Priority is required"));
        }
        if (createTask.getCreatedByUserId() == null || createTask.getCreatedByUserId().isEmpty()) {
            return Uni.createFrom().failure(() -> new InvalidTaskException("CreatedByUserId is required"));
        }

        if (createTask.getDueDate() != null && createTask.getDueDate().isBefore(ZonedDateTimeHelper.now())) {
            return Uni.createFrom().failure(() -> new InvalidTaskException("Due date cannot be in the past"));
        }
        if (createTask.getStatus() != null && createTask.getStatus() != TaskStatus.TO_DO) {
            return Uni.createFrom()
                    .failure(() -> new InvalidTaskException("Only TODO status is allowed when creating a task"));
        }

        Uni<UserClient> createdByUser = userEntityRepository.findById(createTask.getCreatedByUserId());

        Uni<List<UserClient>> assignedUsers = Uni.combine().all().unis(
                createTask.getAssignedUsersId() != null ? createTask.getAssignedUsersId().stream()
                        .map(userEntityRepository::findById)
                        .toList()
                        : List.<Uni<UserClient>>of())
                .with(list -> list.stream()
                        .map(item -> (UserClient) item)
                        .toList());

        return Uni.combine().all().unis(createdByUser, assignedUsers)
                .withUni((u) -> taskEntityRepository.create(createTask));
    }
}
