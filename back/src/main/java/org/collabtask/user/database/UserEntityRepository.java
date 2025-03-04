package org.collabtask.user.database;

import java.util.List;

import org.collabtask.user.core.contracts.IUserRepository;
import org.collabtask.user.core.dto.CreateUser;
import org.collabtask.user.core.dto.UserClient;
import org.collabtask.user.core.exception.UserNotFoundException;

import io.smallrye.mutiny.Uni;

public class UserEntityRepository implements IUserRepository {

    @Override
    public Uni<List<UserClient>> findAll() {
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
