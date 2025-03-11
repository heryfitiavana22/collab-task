package org.collabtask.user;

import org.collabtask.user.core.dto.CreateUser;
import org.collabtask.user.database.UserEntity;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.Getter;

@Getter
@ApplicationScoped
public class UserData {
    private UserEntity userEntity1;
    private UserEntity userEntity2;

    public UserData() {
    }

    public Uni<String> init() {
        userEntity1 = new UserEntity();
        userEntity1.setUsername("eexammplee");
        userEntity1.setEmail("username1@gmail.com");

        userEntity2 = new UserEntity();
        userEntity2.setUsername("usernam2");
        userEntity2.setEmail("username2@gmail.com");

        return userEntity1.persistAndFlush()
                .chain(() -> userEntity2.persistAndFlush())
                .replaceWith(() -> "Users created");
    }

    public CreateUser createUser() {
        return new CreateUser("username", "email@gmail.com");
    }
}
