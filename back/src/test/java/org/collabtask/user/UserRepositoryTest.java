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
                    assertTrue(data.getData().size() > 1);
                });

    }

    @Test
    public void shouldFindById(UniAsserter asserter) {
        asserter.execute(() -> userData.init())
                .assertThat(() -> userRepository.findById(userData.getUserEntity1().getId()), find -> {
                    assertNotNull(find);
                    assertEquals(userData.getUserEntity1().getId(), find.getId());
                });

    }

    @Test
    void shouldNotFoundIfIdNotExist(UniAsserter asserter) {
        asserter.execute(() -> userData.init())
                .assertFailedWith(() -> userRepository.findById("ndksalknk"),
                        throwable -> assertTrue(throwable instanceof UserNotFoundException));
    }

    @Test
    public void shouldFindByEmail(UniAsserter asserter) {
        asserter.execute(() -> userData.init())
                .assertThat(() -> userRepository.findAll(new Pagination()), (data) -> {
                    assertNotNull(data);
                    assertNotNull(data.getData());
                    System.out.println(data.getData());
                    assertTrue(data.getData().size() > 1);
                })
                .assertThat(() -> userRepository.findByEmail(userData.getUserEntity1().getUsername()), find -> {
                    assertNotNull(find);
                    assertEquals(userData.getUserEntity1().getUsername(), find.getUsername());
                });

    }

    @Test
    void shouldCreate(UniAsserter asserter) {
        asserter.execute(() -> userData.init())
                .assertThat(() -> userRepository.create(userData.createUser()), created -> {
                    assertNotNull(created);
                    assertNotNull(created.getId());
                    assertEquals(userData.createUser().getUsername(), created.getUsername());
                    assertEquals(userData.createUser().getEmail(), created.getEmail());
                });
    }

    @Test
    public void shouldDeleteAndShouldNotFoundAfter(UniAsserter asserter) {
        asserter.execute(() -> userData.init())
                .assertThat(() -> userRepository.delete(userData.getUserEntity1().getId()), (deleted) -> {
                    assertNotNull(deleted);
                    assertEquals(userData.getUserEntity1().getId(), deleted.getId());
                })
                .assertFailedWith(() -> userRepository.findById(userData.getUserEntity1().getId()),
                        throwable -> assertTrue(throwable instanceof UserNotFoundException));
    }

}
