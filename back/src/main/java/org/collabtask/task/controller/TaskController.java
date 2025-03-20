package org.collabtask.task.controller;

import org.collabtask.helpers.PaginatedResponse;
import org.collabtask.helpers.Pagination;
import org.collabtask.helpers.exception.BadRequestMessageException;
import org.collabtask.helpers.exception.NotFoundMessageException;
import org.collabtask.task.core.contracts.ITaskController;
import org.collabtask.task.core.contracts.ITaskService;
import org.collabtask.task.core.dto.CreateTask;
import org.collabtask.task.core.dto.TaskClient;
import org.collabtask.task.core.dto.UpdateTask;
import org.collabtask.task.core.exception.InvalidTaskException;
import org.collabtask.task.core.exception.TaskNotFoundException;
import org.collabtask.user.core.exception.UserNotFoundException;

import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

@Path("/tasks")
public class TaskController implements ITaskController {
    private ITaskService taskService;

    public TaskController(ITaskService taskService) {
        this.taskService = taskService;
    }

    @Override
    @POST
    @WithTransaction
    public Uni<TaskClient> create(CreateTask createTask) {
        return taskService.create(createTask)
                .onFailure(InvalidTaskException.class).transform(e -> new BadRequestMessageException(e))
                .onFailure(UserNotFoundException.class).transform(e -> new NotFoundMessageException(e));
    }

    @Override
    @PUT
    @WithTransaction
    @Path("/{id}")
    public Uni<TaskClient> update(@PathParam("id") String id, UpdateTask updateTask) {
        return taskService.update(id, updateTask)
                .onFailure(InvalidTaskException.class).transform(e -> new BadRequestMessageException(e))
                .onFailure(TaskNotFoundException.class).transform(e -> new NotFoundMessageException(e));
    }

    @Override
    @Path("/{id}")
    @GET
    @WithSession
    public Uni<TaskClient> findById(String id) {
        return taskService.findById(id)
                .onFailure(TaskNotFoundException.class).transform(e -> new NotFoundMessageException(e));
    }

    @Override
    public Uni<PaginatedResponse<TaskClient>> findAll(Pagination pagination) {
        return taskService.findAll(pagination);
    }

}
