package org.collabtask.task.core.service.usecases;

import java.util.List;

import org.collabtask.helpers.ZonedDateTimeHelper;
import org.collabtask.task.core.contracts.ITaskRepository;
import org.collabtask.task.core.dto.CreateTask;
import org.collabtask.task.core.dto.TaskClient;
import org.collabtask.task.core.exception.InvalidTaskException;
import org.collabtask.task.core.model.TaskStatus;
import org.collabtask.user.core.contracts.IUserRepository;
import org.collabtask.user.core.dto.UserClient;
import org.collabtask.user.core.exception.UserNotFoundException;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TaskCreator {
    private ITaskRepository taskRepository;
    private IUserRepository userRepository;

    public TaskCreator(ITaskRepository taskRepository, IUserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
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

        return userRepository.findById(createTask.getCreatedByUserId())
                .flatMap(createdByUser -> {
                    Uni<List<UserClient>> assignedUsers = Uni.combine().all().unis(
                            createTask.getAssignedUsersId() != null ? createTask.getAssignedUsersId().stream()
                                    .map(userRepository::findById)
                                    .toList()
                                    : List.<Uni<UserClient>>of())
                            .with(list -> list.stream()
                                    .map(item -> (UserClient) item)
                                    .toList());

                    return assignedUsers
                            .flatMap(users -> taskRepository.create(createTask));
                });

    }
}
