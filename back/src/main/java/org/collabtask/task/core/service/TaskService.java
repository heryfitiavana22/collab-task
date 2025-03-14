package org.collabtask.task.core.service;

import org.collabtask.task.core.contracts.ITaskService;
import org.collabtask.task.core.dto.CreateTask;
import org.collabtask.task.core.dto.TaskClient;

import io.smallrye.mutiny.Uni;

public class TaskService implements ITaskService {

    @Override
    public Uni<TaskClient> create(CreateTask createTask) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'create'");
    }
    
}
