package org.collabtask.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.collabtask.helpers.Pagination;
import org.collabtask.user.core.exception.UserNotFoundException;
import org.collabtask.user.database.UserEntityRepository;
import org.junit.jupiter.api.Test;

import io.quarkus.test.TestReactiveTransaction;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.vertx.UniAsserter;
import jakarta.inject.Inject;

@QuarkusTest
@TestReactiveTransaction
public class UserRepositoryTest {
    @Inject
    UserData userData;

    UserEntityRepository userRepository = new UserEntityRepository();

    @Test
    void shouldFindAll(UniAsserter asserter) {
        asserter.execute(() -> userData.init())
                .assertThat(() -> userRepository.findAll(new Pagination()), (data) -> {
                    assertNotNull(data);
                    assertNotNull(data.getData());
                    assertTrue(data.getData().size() > 0);
                });

    }

    @Test
    public void shouldFindById(UniAsserter asserter) {
        asserter.execute(() -> userData.init())
                .assertThat(() -> userRepository.findById(userData.getUserEntity1().id), post -> {
                    assertNotNull(post);
                    assertEquals(userData.getUserEntity1().id, post.getId());
                });

    }

    @Test
    void shouldNotFoundIfIdNotExist(UniAsserter asserter) {
        asserter.execute(() -> userData.init())
                .assertFailedWith(() -> userRepository.findById("ndksalknk"),
                        throwable -> assertTrue(throwable instanceof UserNotFoundException));
    }

    @Test
    public void shouldFindByUsername(UniAsserter asserter) {
        asserter.execute(() -> userData.init())
                .assertThat(() -> userRepository.findById(userData.getUserEntity1().getUsername()), post -> {
                    assertNotNull(post);
                    assertEquals(userData.getUserEntity1().getUsername(), post.getUsername());
                });

    }

    @Test
    void shouldNotFoundIfUsernameNotExist(UniAsserter asserter) {
        asserter.execute(() -> userData.init())
                .assertFailedWith(() -> userRepository.findByUsername("roigjnfk"),
                        throwable -> assertTrue(throwable instanceof UserNotFoundException));
    }
}
