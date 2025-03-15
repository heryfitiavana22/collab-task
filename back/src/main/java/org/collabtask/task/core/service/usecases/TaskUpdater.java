package org.collabtask.task.core.service.usecases;

import org.collabtask.task.core.dto.TaskClient;
import org.collabtask.task.core.dto.UpdateTask;
import org.collabtask.task.core.exception.InvalidTaskException;
import org.collabtask.task.core.exception.TaskNotFoundException;
import org.collabtask.task.database.TaskEntityRepository;

import io.smallrye.mutiny.Uni;

public class TaskUpdater {
    TaskEntityRepository taskEntityRepository;

    public TaskUpdater(TaskEntityRepository taskEntityRepository) {
        this.taskEntityRepository = taskEntityRepository;
    }

    public Uni<TaskClient> update(String id, UpdateTask updateTask) throws TaskNotFoundException, InvalidTaskException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }
}
