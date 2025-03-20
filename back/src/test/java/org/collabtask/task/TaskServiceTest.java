package org.collabtask.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.collabtask.task.core.exception.TaskNotFoundException;
import org.collabtask.task.core.service.TaskService;
import org.junit.jupiter.api.Test;

import io.quarkus.test.TestReactiveTransaction;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.vertx.UniAsserter;
import jakarta.inject.Inject;

@QuarkusTest
@TestReactiveTransaction
public class TaskServiceTest {
    @Inject
    TaskData taskData;

    @Inject
    TaskService taskService;

    @Test
    void shouldFindById(UniAsserter asserter) {
        asserter.execute(() -> taskData.init())
                .assertThat(() -> taskService.findById(taskData.getTaskTodoNormale().getId()),
                        find -> {
                            assertNotNull(find);
                            assertEquals(taskData.getTaskTodoNormale().getTitle(), find.getTitle());
                        });
    }

    @Test
    void shouldNotFindByIdIfTaskIdDoesNotExist(UniAsserter asserter) {
        asserter.execute(() -> taskData.init())
                .assertFailedWith(() -> taskService.findById("nonexistentTaskId"),
                        throwable -> assertTrue(throwable instanceof TaskNotFoundException));
    }
}
