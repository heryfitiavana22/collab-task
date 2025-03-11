package org.collabtask.task.database;

import org.collabtask.helpers.FindByUserIdPagination;
import org.collabtask.task.core.contracts.ITaskRepository;
import org.collabtask.task.core.model.Task;
import org.collabtask.task.core.model.TaskPriority;
import org.collabtask.task.core.model.TaskStatus;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

public class TaskEntityRepository implements ITaskRepository {

    @Override
    public Multi<Task> findAll(FindByUserIdPagination findByUserIdPagination) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public Multi<Task> findByAssignedUsersId(String userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByAssignedUsersId'");
    }

    @Override
    public Multi<Task> findByCreatedById(String userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByCreatedById'");
    }

    @Override
    public Multi<Task> findByStatus(TaskStatus status) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByStatus'");
    }

    @Override
    public Multi<Task> findByPriorityOrderByDueDate(TaskPriority priority) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByPriorityOrderByDueDate'");
    }

    @Override
    public Multi<Task> findLateTasks() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findLateTasks'");
    }

    @Override
    public Multi<Task> findUrgentTasks() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findUrgentTasks'");
    }

    @Override
    public Uni<TaskEntity> createTask(TaskCreationRepo request) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createTask'");
    }

}
