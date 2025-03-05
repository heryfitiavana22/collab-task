package org.collabtask.user;

import static org.junit.jupiter.api.Assertions.assertNotNull;

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
                .assertThat(() -> userRepository.findAll(), (data) -> {
                    assertNotNull(data);
                });

    }
}
