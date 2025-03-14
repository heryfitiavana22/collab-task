package org.collabtask.user.controller;

import org.collabtask.helpers.PaginatedResponse;
import org.collabtask.helpers.Pagination;
import org.collabtask.user.core.contracts.IUserControllerQuarkus;
import org.collabtask.user.core.contracts.IUserService;
import org.collabtask.user.core.dto.CreateUser;
import org.collabtask.user.core.dto.UserClient;
import org.collabtask.user.core.exception.UserNotFoundException;

import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

@Path("/users")
public class UserControllerQuarkus implements IUserControllerQuarkus {
    private IUserService userService;

    public UserControllerQuarkus(IUserService userService) {
        this.userService = userService;
    }

    @Override
    @GET
    @WithSession
    public Uni<PaginatedResponse<UserClient>> findAll(Pagination pagination) {
        return userService.findAll(pagination);
    }

    @Override
    @GET
    @WithSession
    @Path("/{id}")
    public Uni<UserClient> findById(@PathParam("id") String id) {
        return userService.findById(id)
                .onFailure(UserNotFoundException.class).transform(e -> new BadRequestException(e));
    }

    @Override
    @GET
    @WithSession
    @Path("/email/{email}")
    public Uni<UserClient> findByEmail(@PathParam("email") String email) {
        return userService.findByEmail(email)
                .onFailure(UserNotFoundException.class).transform(e -> new BadRequestException(e));
    }

    @Override
    @POST
    @WithTransaction
    public Uni<UserClient> create(CreateUser data) {
        return userService.create(data);
    }

    @Override
    @DELETE
    @Path("/{id}")
    @WithTransaction
    public Uni<UserClient> delete(@PathParam("id") String id) {
        return userService.delete(id)
                .onFailure(UserNotFoundException.class).transform(e -> new BadRequestException(e));
    }

}
