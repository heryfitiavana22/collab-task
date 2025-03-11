package org.collabtask.task.core.contracts;

import org.collabtask.helpers.FindByUserIdPagination;
import org.collabtask.task.core.dto.CreateTask;
import org.collabtask.task.core.dto.TaskClient;
import org.collabtask.task.core.model.TaskPriority;
import org.collabtask.task.core.model.TaskStatus;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

public interface ITaskRepository {
    Multi<TaskClient> findAll(FindByUserIdPagination findByUserIdPagination);

    Multi<TaskClient> findByAssignedUsersId(String userId);

    Multi<TaskClient> findByCreatedById(String userId);

    Multi<TaskClient> findByStatus(TaskStatus status);

    Multi<TaskClient> findByPriorityOrderByDueDate(TaskPriority priority);

    Multi<TaskClient> findLateTasks();

    Multi<TaskClient> findUrgentTasks();

    Uni<TaskClient> createTask(CreateTask request);

}
