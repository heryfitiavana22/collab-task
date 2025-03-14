package org.collabtask.task.core.contracts;

import org.collabtask.task.core.dto.CreateTask;
import org.collabtask.task.core.dto.TaskClient;
import org.collabtask.task.core.exception.InvalidTaskException;
import org.collabtask.user.core.exception.UserNotFoundException;

import io.smallrye.mutiny.Uni;

public interface ITaskService {
    Uni<TaskClient> create(CreateTask createTask) throws InvalidTaskException, UserNotFoundException;
}
