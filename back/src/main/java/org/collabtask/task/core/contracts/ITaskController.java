package org.collabtask.task.core.contracts;

import org.collabtask.task.core.dto.CreateTask;
import org.collabtask.task.core.dto.TaskClient;
import org.collabtask.task.core.dto.UpdateTask;

import io.smallrye.mutiny.Uni;

public interface ITaskController {
    Uni<TaskClient> create(CreateTask createTask);

    Uni<TaskClient> update(String id, UpdateTask updateTask);

    Uni<TaskClient> findById(String id);
}
