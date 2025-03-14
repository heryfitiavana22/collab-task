package org.collabtask.user.core.contracts;

import org.collabtask.helpers.PaginatedResponse;
import org.collabtask.helpers.Pagination;
import org.collabtask.user.core.dto.CreateUser;
import org.collabtask.user.core.dto.UserClient;

import io.smallrye.mutiny.Uni;

public interface IUserControllerQuarkus {
    Uni<PaginatedResponse<UserClient>> findAll(Pagination pagination);

    Uni<UserClient> findById(String id);

    Uni<UserClient> findByEmail(String email);

    Uni<UserClient> create(CreateUser data);

    Uni<UserClient> delete(String id);
}
