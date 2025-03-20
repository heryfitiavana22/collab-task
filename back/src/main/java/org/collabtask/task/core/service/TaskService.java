package org.collabtask.task.core.service;

import org.collabtask.task.core.contracts.ITaskRepository;
import org.collabtask.task.core.contracts.ITaskService;
import org.collabtask.task.core.dto.CreateTask;
import org.collabtask.task.core.dto.TaskClient;
import org.collabtask.task.core.dto.UpdateTask;
import org.collabtask.task.core.exception.InvalidTaskException;
import org.collabtask.task.core.exception.TaskNotFoundException;
import org.collabtask.task.core.service.usecases.TaskCreator;
import org.collabtask.task.core.service.usecases.TaskUpdater;
import org.collabtask.user.core.exception.UserNotFoundException;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TaskService implements ITaskService {
    private ITaskRepository taskEntityRepository;
    private TaskCreator taskCreator;
    private TaskUpdater taskUpdater;

    public TaskService(ITaskRepository taskEntityRepository, TaskCreator taskCreator, TaskUpdater taskUpdater) {
        this.taskEntityRepository = taskEntityRepository;
        this.taskCreator = taskCreator;
        this.taskUpdater = taskUpdater;
    }

    @Override
    public Uni<TaskClient> create(CreateTask createTask) throws InvalidTaskException, UserNotFoundException {
        return taskCreator.create(createTask);
    }

    @Override
    public Uni<TaskClient> update(String id, UpdateTask updateTask) throws TaskNotFoundException, InvalidTaskException {
        return taskUpdater.update(id, updateTask);
    }

    @Override
    public Uni<TaskClient> findById(String id) throws TaskNotFoundException {
        return taskEntityRepository.findById(id);
    }
}
