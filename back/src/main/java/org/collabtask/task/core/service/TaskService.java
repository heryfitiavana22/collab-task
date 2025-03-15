package org.collabtask.task.core.service;

import org.collabtask.task.core.contracts.ITaskService;
import org.collabtask.task.core.dto.CreateTask;
import org.collabtask.task.core.dto.TaskClient;
import org.collabtask.task.core.dto.UpdateTask;
import org.collabtask.task.core.exception.InvalidTaskException;
import org.collabtask.task.core.exception.TaskNotFoundException;
import org.collabtask.task.core.service.usecases.TaskCreator;
import org.collabtask.task.core.service.usecases.TaskUpdater;
import org.collabtask.task.database.TaskEntityRepository;
import org.collabtask.user.core.exception.UserNotFoundException;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class TaskService implements ITaskService {

    @Inject
    TaskEntityRepository taskEntityRepository;

    @Inject
    TaskCreator taskCreator;

    @Inject
    TaskUpdater taskUpdater;

    @Override
    public Uni<TaskClient> create(CreateTask createTask) throws InvalidTaskException, UserNotFoundException {
        return taskCreator.create(createTask);
    }

    @Override
    public Uni<TaskClient> update(String id, UpdateTask updateTask) throws TaskNotFoundException, InvalidTaskException {
        return taskUpdater.update(id, updateTask);
    }
}
