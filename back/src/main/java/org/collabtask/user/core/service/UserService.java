package org.collabtask.user.core.service;

import java.util.List;

import org.collabtask.helpers.PaginatedResponse;
import org.collabtask.user.core.contracts.IUserService;
import org.collabtask.user.core.dto.CreateUser;
import org.collabtask.user.core.dto.UserClient;
import org.collabtask.user.core.exception.UserNotFoundException;

import io.smallrye.mutiny.Uni;

public class UserService implements IUserService {

    @Override
    public Uni<PaginatedResponse<List<UserClient>>> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public Uni<UserClient> findById(String id) throws UserNotFoundException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public Uni<UserClient> findByUsername(String username) throws UserNotFoundException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByUsername'");
    }

    @Override
    public Uni<UserClient> create(CreateUser data) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'create'");
    }

    @Override
    public Uni<UserClient> delete(String id) throws UserNotFoundException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

}
