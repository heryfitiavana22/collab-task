package org.collabtask.task.core.contracts;

import org.collabtask.task.core.dto.CreateTask;
import org.collabtask.task.core.dto.TaskClient;
import org.collabtask.task.core.dto.UpdateTask;
import org.collabtask.task.core.exception.InvalidTaskException;
import org.collabtask.task.core.exception.TaskNotFoundException;
import org.collabtask.user.core.exception.UserNotFoundException;

import io.smallrye.mutiny.Uni;

public interface ITaskService {
    Uni<TaskClient> create(CreateTask createTask) throws InvalidTaskException, UserNotFoundException;

    public Uni<TaskClient> update(String id, UpdateTask updateTask) throws TaskNotFoundException, InvalidTaskException;
}
