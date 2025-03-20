package org.collabtask.task.controller;

import org.collabtask.task.core.contracts.ITaskController;
import org.collabtask.task.core.contracts.ITaskService;
import org.collabtask.task.core.dto.CreateTask;
import org.collabtask.task.core.dto.TaskClient;
import org.collabtask.task.core.dto.UpdateTask;

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
        return taskService.create(createTask);
    }

    @Override
    @PUT
    @WithTransaction
    @Path("/{id}")
    public Uni<TaskClient> update(@PathParam("id") String id, UpdateTask updateTask) {
        return taskService.update(id, updateTask);
    }

    @Override
    @Path("/{id}")
    @GET
    @WithSession
    public Uni<TaskClient> findById(String id) {
        return taskService.findById(id);
    }

}
