package org.collabtask.task.database;

import org.collabtask.helpers.FindByUserIdPagination;
import org.collabtask.task.core.contracts.ITaskRepository;
import org.collabtask.task.core.dto.CreateTask;
import org.collabtask.task.core.dto.TaskClient;
import org.collabtask.task.core.model.TaskPriority;
import org.collabtask.task.core.model.TaskStatus;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

public class TaskEntityRepository implements ITaskRepository {

    @Override
    public Multi<TaskClient> findAll(FindByUserIdPagination findByUserIdPagination) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByAssignedUsersId'");
    }

    @Override
    public Multi<TaskClient> findByAssignedUsersId(String userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByAssignedUsersId'");
    }

    @Override
    public Multi<TaskClient> findByCreatedById(String userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByCreatedById'");
    }

    @Override
    public Multi<TaskClient> findByStatus(TaskStatus status) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByStatus'");
    }

    @Override
    public Multi<TaskClient> findByPriorityOrderByDueDate(TaskPriority priority) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByPriorityOrderByDueDate'");
    }

    @Override
    public Multi<TaskClient> findLateTasks() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findLateTasks'");
    }

    @Override
    public Multi<TaskClient> findUrgentTasks() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findUrgentTasks'");
    }

    @Override
    public Uni<TaskClient> createTask(CreateTask request) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createTask'");
    }

}
