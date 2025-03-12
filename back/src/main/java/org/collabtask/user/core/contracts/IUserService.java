package org.collabtask.user.core.contracts;

import org.collabtask.helpers.PaginatedResponse;
import org.collabtask.helpers.Pagination;
import org.collabtask.user.core.dto.CreateUser;
import org.collabtask.user.core.dto.UserClient;
import org.collabtask.user.core.exception.UserNotFoundException;

import io.smallrye.mutiny.Uni;

public interface IUserService {
    Uni<PaginatedResponse<UserClient>> findAll(Pagination pagination);

    Uni<UserClient> findById(String id) throws UserNotFoundException;

    Uni<UserClient> findByEmail(String email) throws UserNotFoundException;

    Uni<UserClient> create(CreateUser data);

    Uni<UserClient> delete(String id) throws UserNotFoundException;
}
