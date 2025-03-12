package org.collabtask.task.database;

import org.collabtask.helpers.FindByUserIdPagination;
import org.collabtask.helpers.PaginatedResponse;
import org.collabtask.task.core.contracts.ITaskRepository;
import org.collabtask.task.core.dto.CreateTask;
import org.collabtask.task.core.dto.TaskClient;
import org.collabtask.task.core.model.TaskPriority;
import org.collabtask.task.core.model.TaskStatus;

import io.smallrye.mutiny.Uni;

public class TaskEntityRepository implements ITaskRepository {

    @Override
    public Uni<PaginatedResponse<TaskClient>> findAll(FindByUserIdPagination findByUserIdPagination) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByAssignedUsersId'");
    }

    @Override
    public Uni<PaginatedResponse<TaskClient>> findByAssignedUsersId(String userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByAssignedUsersId'");
    }

    @Override
    public Uni<PaginatedResponse<TaskClient>> findByCreatedById(String userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByCreatedById'");
    }

    @Override
    public Uni<PaginatedResponse<TaskClient>> findByStatus(TaskStatus status) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByStatus'");
    }

    @Override
    public Uni<PaginatedResponse<TaskClient>> findByPriorityOrderByDueDate(TaskPriority priority) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByPriorityOrderByDueDate'");
    }

    @Override
    public Uni<PaginatedResponse<TaskClient>> findLateTasks() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findLateTasks'");
    }

    @Override
    public Uni<PaginatedResponse<TaskClient>> findUrgentTasks() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findUrgentTasks'");
    }

    @Override
    public Uni<TaskClient> createTask(CreateTask request) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createTask'");
    }

}
