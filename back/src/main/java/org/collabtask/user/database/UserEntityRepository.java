package org.collabtask.user.database;

import java.util.List;
import java.util.stream.Collectors;

import org.collabtask.helpers.PaginatedResponse;
import org.collabtask.helpers.Pagination;
import org.collabtask.user.core.contracts.IUserRepository;
import org.collabtask.user.core.dto.CreateUser;
import org.collabtask.user.core.dto.UserClient;
import org.collabtask.user.core.exception.UserNotFoundException;

import io.smallrye.mutiny.Uni;

public class UserEntityRepository implements IUserRepository {

    @Override
    public Uni<PaginatedResponse<UserClient>> findAll(Pagination pagination) {
        Uni<List<UserEntity>> entities = UserEntity.findAll().page(pagination.getPage(), pagination.getSize()).list();
        Uni<Long> total = UserEntity.count();

        Uni<List<UserClient>> usersClient = entities.map(userEntities -> userEntities.stream()
                .map(entity -> entity.toClient())
                .collect(Collectors.toList()));

        return Uni.combine().all().unis(usersClient, total).asTuple()
                .map(tuple -> {
                    int totalPage = (int) Math.ceil(tuple.getItem2().doubleValue() / pagination.getSize());
                    return new PaginatedResponse<>(
                            new Pagination(pagination.getPage(), pagination.getSize()),
                            tuple.getItem1(), totalPage);
                });
    }

    @Override
    public Uni<UserClient> findById(String id) throws UserNotFoundException {
        Uni<UserEntity> entity = UserEntity.findById(id);
        return entity
                .onItem().ifNotNull().transform(userEntity -> userEntity.toClient())
                .onItem().ifNull().failWith(() -> new UserNotFoundException(id));
    }

    @Override
    public Uni<UserClient> findByEmail(String username) throws UserNotFoundException {
        Uni<UserEntity> find = UserEntity.find("username", username).firstResult();
        return find.onItem().transformToUni(entity -> {
            System.out.println(entity);
            if (entity == null)
                return Uni.createFrom().failure(() -> new UserNotFoundException(" username: " + username));
            return Uni.createFrom().item((entity.toClient()));
        });
    }

    @Override
    public Uni<UserClient> create(CreateUser data) {
        UserEntity find = new UserEntity(data);
        Uni<UserEntity> created = find.persist();
        return created.map(pe -> pe.toClient());
    }

    @Override
    public Uni<UserClient> delete(String id) throws UserNotFoundException {
        Uni<UserEntity> entity = UserEntity.findById(id);
        return entity
                .onItem().ifNotNull()
                .transformToUni(userEntity -> userEntity.delete().replaceWith(userEntity.toClient()))
                .onItem().ifNull().failWith(() -> new UserNotFoundException(id));
    }

}
