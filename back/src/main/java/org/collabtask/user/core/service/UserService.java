package org.collabtask.user.core.service;

import org.collabtask.helpers.PaginatedResponse;
import org.collabtask.helpers.Pagination;
import org.collabtask.user.core.contracts.IUserRepository;
import org.collabtask.user.core.contracts.IUserService;
import org.collabtask.user.core.dto.CreateUser;
import org.collabtask.user.core.dto.UserClient;
import org.collabtask.user.core.exception.UserNotFoundException;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserService implements IUserService {
    private IUserRepository userRepository;

    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Uni<PaginatedResponse<UserClient>> findAll(Pagination pagination) {
        if (pagination == null) {
            pagination = new Pagination();
        }
        return userRepository.findAll(pagination);
    }

    @Override
    public Uni<UserClient> findById(String id) throws UserNotFoundException {
        return userRepository.findById(id);
    }

    @Override
    public Uni<UserClient> findByEmail(String email) throws UserNotFoundException {
        return userRepository.findByEmail(email);
    }

    @Override
    public Uni<UserClient> create(CreateUser data) {
        return userRepository.create(data);
    }

    @Override
    public Uni<UserClient> delete(String id) throws UserNotFoundException {
        return userRepository.delete(id);
    }

}
