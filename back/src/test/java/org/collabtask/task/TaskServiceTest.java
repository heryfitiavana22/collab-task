package org.collabtask.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.collabtask.helpers.ZonedDateTimeHelper;
import org.collabtask.task.core.dto.CreateTask;
import org.collabtask.task.core.exception.InvalidTaskException;
import org.collabtask.task.core.model.TaskStatus;
import org.collabtask.task.core.service.TaskService;
import org.collabtask.user.core.exception.UserNotFoundException;
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
    void shouldCreateTask(UniAsserter asserter) {
        asserter.execute(() -> taskData.init())
                .assertThat(() -> taskService.create(taskData.createTask()), created -> {
                    assertNotNull(created);
                    assertNotNull(created.getId());
                    assertEquals(taskData.createTask().getTitle(), created.getTitle());
                    assertEquals(taskData.createTask().getPriority(), created.getPriority());
                    assertEquals(taskData.createTask().getStatus(), created.getStatus());
                    assertEquals(taskData.createTask().getCreatedByUserId(), created.getCreatedBy().getId());
                });
    }

    @Test
    void shouldNotCreateTaskWithInvalidDueDate(UniAsserter asserter) {
        asserter.execute(() -> taskData.init())
                .assertFailedWith(() -> {
                    CreateTask invalidTask = taskData.createTask();
                    invalidTask.setDueDate(ZonedDateTimeHelper.now().minusDays(1));
                    return taskService.create(invalidTask);
                }, throwable -> {
                    assertTrue(throwable instanceof InvalidTaskException);
                });
    }

    @Test
    void shouldNotCreateTaskWithoutStatusOtherThanToDo(UniAsserter asserter) {
        asserter.execute(() -> taskData.init())
                .assertFailedWith(() -> {
                    CreateTask invalidTask = taskData.createTask();
                    invalidTask.setStatus(TaskStatus.COMPLETED);
                    return taskService.create(invalidTask);
                }, throwable -> {
                    assertTrue(throwable instanceof InvalidTaskException);
                });
    }

    @Test
    void shouldNotCreateTaskWithoutTitle(UniAsserter asserter) {
        asserter.execute(() -> taskData.init())
                .assertFailedWith(() -> {
                    CreateTask invalidTask = taskData.createTask();
                    invalidTask.setTitle(null);
                    return taskService.create(invalidTask);
                }, throwable -> {
                    assertTrue(throwable instanceof InvalidTaskException);
                });
    }

    @Test
    void shouldNotCreateTaskWithoutPriority(UniAsserter asserter) {
        asserter.execute(() -> taskData.init())
                .assertFailedWith(
                        () -> {
                            CreateTask invalidTask = taskData.createTask();
                            invalidTask.setPriority(null);

                            return taskService.create(invalidTask);
                        }, throwable -> {
                            assertTrue(throwable instanceof InvalidTaskException);
                        });
    }

    @Test
    void shouldNotCreateTaskWithoutCreatedByUserId(UniAsserter asserter) {
        asserter.execute(() -> taskData.init())
                .assertFailedWith(
                        () -> {
                            CreateTask invalidTask = taskData.createTask();
                            invalidTask.setCreatedByUserId(null);
                            return taskService.create(invalidTask);
                        }, throwable -> {
                            assertTrue(throwable instanceof InvalidTaskException);
                        });
    }

    @Test
    void shouldNotCreateTaskWithUserIdNotFoundInCreatedByUserId(UniAsserter asserter) {
        asserter.execute(() -> taskData.init())
                .assertFailedWith(
                        () -> {
                            CreateTask invalidTask = taskData.createTask();
                            invalidTask.setCreatedByUserId("dsaflkjdk");
                            return taskService.create(invalidTask);
                        }, throwable -> {
                            assertTrue(throwable instanceof UserNotFoundException);
                        });
    }

    @Test
    void shouldNotCreateTaskWithUserIdNotFoundInAssignedUsersId(UniAsserter asserter) {
        asserter.execute(() -> taskData.init())
                .assertFailedWith(
                        () -> {
                            List<String> assignedUsersId = new ArrayList<>();
                            assignedUsersId.add("cnvjzxlkf");
                            CreateTask invalidTask = taskData.createTask();
                            invalidTask.setAssignedUsersId(assignedUsersId);
                            return taskService.create(invalidTask);
                        }, throwable -> {
                            assertTrue(throwable instanceof UserNotFoundException);
                        });
    }
}
