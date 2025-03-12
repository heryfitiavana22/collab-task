package org.collabtask.task.core.contracts;

import org.collabtask.helpers.FindByUserIdPagination;
import org.collabtask.helpers.PaginatedResponse;
import org.collabtask.task.core.dto.CreateTask;
import org.collabtask.task.core.dto.TaskClient;
import org.collabtask.task.core.model.TaskPriority;
import org.collabtask.task.core.model.TaskStatus;

import io.smallrye.mutiny.Uni;

public interface ITaskRepository {
    Uni<PaginatedResponse<TaskClient>> findAll(FindByUserIdPagination findByUserIdPagination);

    Uni<PaginatedResponse<TaskClient>> findByAssignedUsersId(String userId);

    Uni<PaginatedResponse<TaskClient>> findByCreatedById(String userId);

    Uni<PaginatedResponse<TaskClient>> findByStatus(TaskStatus status);

    Uni<PaginatedResponse<TaskClient>> findByPriorityOrderByDueDate(TaskPriority priority);

    Uni<PaginatedResponse<TaskClient>> findLateTasks();

    Uni<PaginatedResponse<TaskClient>> findUrgentTasks();

    Uni<TaskClient> createTask(CreateTask request);

}
