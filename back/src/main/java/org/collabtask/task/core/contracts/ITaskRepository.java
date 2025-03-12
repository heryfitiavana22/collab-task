package org.collabtask.task.core.contracts;

import org.collabtask.helpers.FindByUserIdPagination;
import org.collabtask.helpers.PaginatedResponse;
import org.collabtask.helpers.Pagination;
import org.collabtask.task.core.dto.CreateTask;
import org.collabtask.task.core.dto.TaskClient;
import org.collabtask.task.core.dto.UpdateTask;
import org.collabtask.task.core.exception.TaskNotFoundException;
import org.collabtask.task.core.model.TaskPriority;
import org.collabtask.task.core.model.TaskStatus;

import io.smallrye.mutiny.Uni;

public interface ITaskRepository {
    Uni<PaginatedResponse<TaskClient>> findAll(Pagination pagination);

    Uni<PaginatedResponse<TaskClient>> findByAssignedUsersId(String userId);

    Uni<PaginatedResponse<TaskClient>> findByCreatedByUserId(FindByUserIdPagination findByUserIdPagination);

    Uni<PaginatedResponse<TaskClient>> findByStatus(TaskStatus status);

    Uni<PaginatedResponse<TaskClient>> findByPriorityOrderByDueDate(TaskPriority priority);

    Uni<PaginatedResponse<TaskClient>> findLate();

    Uni<PaginatedResponse<TaskClient>> findUrgent();

    Uni<TaskClient> create(CreateTask createTask);

    Uni<TaskClient> update(String id, UpdateTask updateTask) throws TaskNotFoundException;

}
