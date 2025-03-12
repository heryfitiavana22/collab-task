package org.collabtask.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.collabtask.helpers.FindByUserIdPagination;
import org.collabtask.helpers.Pagination;
import org.collabtask.task.core.exception.TaskNotFoundException;
import org.collabtask.task.database.TaskEntityRepository;
import org.junit.jupiter.api.Test;

import io.quarkus.test.TestReactiveTransaction;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.vertx.UniAsserter;
import jakarta.inject.Inject;

@QuarkusTest
@TestReactiveTransaction
public class TaskRepositoryTest {
    @Inject
    TaskData taskData;

    TaskEntityRepository taskRepository = new TaskEntityRepository();

    @Test
    void shouldFindAll(UniAsserter asserter) {
        asserter.execute(() -> taskData.init())
                .assertThat(() -> taskRepository.findAll(new Pagination()), (data) -> {
                    assertNotNull(data);
                    assertNotNull(data.getData());
                    assertTrue(data.getData().size() > 1);
                });
    }

    @Test
    void shouldFindByCreatedByUserId(UniAsserter asserter) {
        asserter.execute(() -> taskData.init())
                .assertThat(() -> taskRepository.findByCreatedByUserId(
                        new FindByUserIdPagination(taskData.getTaskTodoNormale().getCreatedBy().getId())),
                        (data) -> {
                            assertNotNull(data);
                            assertNotNull(data.getData());
                            assertTrue(data.getData().size() > 0);
                        });
    }

    @Test
    void shouldNotFindByCreatedByUserIdIfUserIdDoesNotExist(UniAsserter asserter) {
        asserter.execute(() -> taskData.init())
                .assertThat(() -> taskRepository.findByCreatedByUserId(
                        new FindByUserIdPagination("nonexistentUserId")),
                        (data) -> {
                            assertNotNull(data);
                            assertTrue(data.getData().isEmpty());
                        });
    }

    @Test
    void shouldCreate(UniAsserter asserter) {
        asserter.execute(() -> taskData.init())
                .assertThat(() -> taskRepository.create(taskData.createTask()), created -> {
                    assertNotNull(created);
                    assertNotNull(created.getId());
                    assertEquals(taskData.createTask().getTitle(), created.getTitle());
                });
    }

    @Test
    void shouldUpdate(UniAsserter asserter) {
        asserter.execute(() -> taskData.init())
                .assertThat(() -> taskRepository.update(taskData.getTaskTodoNormale().getId(), taskData.updateTask()),
                        updated -> {
                            assertNotNull(updated);
                            assertEquals(taskData.updateTask().getTitle(), updated.getTitle());
                        });
    }

    @Test
    void shouldNotUpdateIfTaskDoesNotExist(UniAsserter asserter) {
        asserter.execute(() -> taskData.init())
                .assertFailedWith(() -> taskRepository.update("nonexistentTaskId", taskData.updateTask()),
                        throwable -> assertTrue(throwable instanceof TaskNotFoundException));
    }
}
