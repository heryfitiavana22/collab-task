package org.collabtask.user.core.contracts;

import java.util.List;

import org.collabtask.helpers.PaginatedResponse;
import org.collabtask.helpers.Pagination;
import org.collabtask.user.core.dto.CreateUser;
import org.collabtask.user.core.dto.UserClient;
import org.collabtask.user.core.exception.UserNotFoundException;

import io.smallrye.mutiny.Uni;

public interface IUserRepository {
    Uni<PaginatedResponse<List<UserClient>>> findAll(Pagination pagination);

    Uni<UserClient> findById(String id) throws UserNotFoundException;

    Uni<UserClient> findByUsername(String username) throws UserNotFoundException;

    Uni<UserClient> create(CreateUser data);

    Uni<UserClient> delete(String id) throws UserNotFoundException;
}
