package org.collabtask.task.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.collabtask.helpers.ZonedDateTimeHelper;
import org.collabtask.task.TaskData;
import org.collabtask.task.core.dto.UpdateTask;
import org.collabtask.task.core.exception.InvalidTaskException;
import org.collabtask.task.core.exception.TaskNotFoundException;
import org.collabtask.task.core.model.TaskStatus;
import org.collabtask.task.core.service.usecases.TaskUpdater;
import org.junit.jupiter.api.Test;

import io.quarkus.test.TestReactiveTransaction;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.vertx.UniAsserter;
import jakarta.inject.Inject;

@QuarkusTest
@TestReactiveTransaction
public class TaskUpdaterTest {
    @Inject
    TaskData taskData;

    @Inject
    TaskUpdater taskUpdater;

    @Test
    void shouldUpdateTask(UniAsserter asserter) {
        asserter.execute(() -> taskData.init())
                .assertThat(() -> taskUpdater.update(taskData.getTaskTodoNormale().getId(), taskData.updateTask()),
                        updated -> {
                            assertNotNull(updated);
                            assertEquals(taskData.updateTask().getTitle(), updated.getTitle());
                            assertEquals(taskData.updateTask().getPriority(), updated.getPriority());
                            assertEquals(taskData.updateTask().getStatus(), updated.getStatus());
                        });
    }

    @Test
    void shouldUpdatePartialTask(UniAsserter asserter) {
        UpdateTask updateTaskPartial = new UpdateTask();
        updateTaskPartial.setTitle("title only");
        asserter.execute(() -> taskData.init())
                .assertThat(() -> taskUpdater.update(taskData.getTaskTodoNormale().getId(), updateTaskPartial),
                        updated -> {
                            assertNotNull(updated);
                            assertEquals(updateTaskPartial.getTitle(), updated.getTitle());
                        });
    }

    @Test
    void shouldNotUpdateNonExistentTask(UniAsserter asserter) {
        asserter.execute(() -> taskData.init())
                .assertFailedWith(() -> taskUpdater.update("nonexistentId", taskData.updateTask()), throwable -> {
                    assertTrue(throwable instanceof TaskNotFoundException);
                });
    }

    @Test
    void shouldNotUpdateTaskWithInvalidDueDateAndCompleted(UniAsserter asserter) {
        asserter.execute(() -> taskData.init())
                .assertFailedWith(() -> {
                    UpdateTask invalidTask = taskData.updateTask();
                    invalidTask.setDueDate(ZonedDateTimeHelper.now().minusDays(1));
                    return taskUpdater.update(taskData.getTaskCompletedHaute().getId(), invalidTask);
                }, throwable -> {
                    assertTrue(throwable instanceof InvalidTaskException);
                });
    }

    @Test
    void shouldUpdateOverdueTaskStatusToToDoOrInProgressIfDueDateFuture(UniAsserter asserter) {
        asserter.execute(() -> taskData.init())
                .assertThat(() -> {
                    UpdateTask updateTask = new UpdateTask();
                    updateTask.setDueDate(ZonedDateTimeHelper.now().plusDays(3));
                    return taskUpdater.update(taskData.getTaskOverdueNormale().getId(), updateTask);
                }, updated -> {
                    assertNotNull(updated);
                    assertTrue(
                            updated.getStatus() == TaskStatus.TO_DO || updated.getStatus() == TaskStatus.IN_PROGRESS);
                });
    }

    @Test
    void shouldUpdateStatusToOverdueIfDueDatePassed(UniAsserter asserter) {
        asserter.execute(() -> taskData.init())
                .assertThat(() -> {
                    UpdateTask updateTask = taskData.updateTask();
                    updateTask.setDueDate(ZonedDateTimeHelper.now().minusDays(1));
                    return taskUpdater.update(taskData.getTaskTodoNormale().getId(), updateTask);
                }, updated -> {
                    assertNotNull(updated);
                    assertEquals(TaskStatus.OVERDUE, updated.getStatus());
                });
    }

    @Test
    void shouldNotUpdateStatusFromCompleted(UniAsserter asserter) {
        asserter.execute(() -> taskData.init())
                .assertFailedWith(() -> {
                    UpdateTask updateTask = taskData.updateTask();
                    updateTask.setStatus(TaskStatus.BLOCKED);
                    return taskUpdater.update(taskData.getTaskCompletedHaute().getId(), updateTask);
                }, throwable -> {
                    assertTrue(throwable instanceof InvalidTaskException);
                });
    }

    @Test
    void shouldUpdateStatusFromToDoToInProgress(UniAsserter asserter) {
        asserter.execute(() -> taskData.init())
                .assertThat(() -> {
                    UpdateTask updateTask = taskData.updateTask();
                    updateTask.setStatus(TaskStatus.IN_PROGRESS);
                    return taskUpdater.update(taskData.getTaskTodoNormale().getId(), updateTask);
                }, updated -> {
                    assertNotNull(updated);
                    assertEquals(TaskStatus.IN_PROGRESS, updated.getStatus());
                });
    }

    @Test
    void shouldUpdateStatusFromToDoToBlocked(UniAsserter asserter) {
        asserter.execute(() -> taskData.init())
                .assertThat(() -> {
                    UpdateTask updateTask = taskData.updateTask();
                    updateTask.setStatus(TaskStatus.BLOCKED);
                    return taskUpdater.update(taskData.getTaskTodoNormale().getId(), updateTask);
                }, updated -> {
                    assertNotNull(updated);
                    assertEquals(TaskStatus.BLOCKED, updated.getStatus());
                });
    }

    @Test
    void shouldUpdateStatusFromToDoToCompleted(UniAsserter asserter) {
        asserter.execute(() -> taskData.init())
                .assertThat(() -> {
                    UpdateTask updateTask = taskData.updateTask();
                    updateTask.setStatus(TaskStatus.COMPLETED);
                    return taskUpdater.update(taskData.getTaskTodoNormale().getId(), updateTask);
                }, updated -> {
                    assertNotNull(updated);
                    assertEquals(TaskStatus.COMPLETED, updated.getStatus());
                });
    }

    @Test
    void shouldUpdateStatusFromInProgressToBlocked(UniAsserter asserter) {
        asserter.execute(() -> taskData.init())
                .assertThat(() -> {
                    UpdateTask updateTask = taskData.updateTask();
                    updateTask.setStatus(TaskStatus.BLOCKED);
                    return taskUpdater.update(taskData.getTaskProgressHaute().getId(), updateTask);
                }, updated -> {
                    assertNotNull(updated);
                    assertEquals(TaskStatus.BLOCKED, updated.getStatus());
                });
    }

    @Test
    void shouldUpdateStatusFromInProgressToCompleted(UniAsserter asserter) {
        asserter.execute(() -> taskData.init())
                .assertThat(() -> {
                    UpdateTask updateTask = taskData.updateTask();
                    updateTask.setStatus(TaskStatus.COMPLETED);
                    return taskUpdater.update(taskData.getTaskProgressHaute().getId(), updateTask);
                }, updated -> {
                    assertNotNull(updated);
                    assertEquals(TaskStatus.COMPLETED, updated.getStatus());
                });
    }

    @Test
    void shouldUpdateStatusFromBlockedToToDo(UniAsserter asserter) {
        asserter.execute(() -> taskData.init())
                .assertThat(() -> {
                    UpdateTask updateTask = taskData.updateTask();
                    updateTask.setStatus(TaskStatus.TO_DO);
                    return taskUpdater.update(taskData.getTaskBlockedHaute().getId(), updateTask);
                }, updated -> {
                    assertNotNull(updated);
                    assertEquals(TaskStatus.TO_DO, updated.getStatus());
                });
    }

    @Test
    void shouldUpdateStatusFromBlockedToInProgress(UniAsserter asserter) {
        asserter.execute(() -> taskData.init())
                .assertThat(() -> {
                    UpdateTask updateTask = taskData.updateTask();
                    updateTask.setStatus(TaskStatus.IN_PROGRESS);
                    return taskUpdater.update(taskData.getTaskBlockedHaute().getId(), updateTask);
                }, updated -> {
                    assertNotNull(updated);
                    assertEquals(TaskStatus.IN_PROGRESS, updated.getStatus());
                });
    }

    @Test
    void shouldNotUpdateStatusFromToDoToOverdue(UniAsserter asserter) {
        asserter.execute(() -> taskData.init())
                .assertFailedWith(() -> {
                    UpdateTask updateTask = taskData.updateTask();
                    updateTask.setStatus(TaskStatus.OVERDUE);
                    return taskUpdater.update(taskData.getTaskTodoNormale().getId(), updateTask);
                }, throwable -> {
                    assertTrue(throwable instanceof InvalidTaskException);
                });
    }

    @Test
    void shouldNotUpdateStatusFromInProgressToOverdue(UniAsserter asserter) {
        asserter.execute(() -> taskData.init())
                .assertFailedWith(() -> {
                    UpdateTask updateTask = taskData.updateTask();
                    updateTask.setStatus(TaskStatus.OVERDUE);
                    return taskUpdater.update(taskData.getTaskProgressHaute().getId(), updateTask);
                }, throwable -> {
                    assertTrue(throwable instanceof InvalidTaskException);
                });
    }

    @Test
    void shouldNotUpdateStatusFromInProgressToToDo(UniAsserter asserter) {
        asserter.execute(() -> taskData.init())
                .assertFailedWith(() -> {
                    UpdateTask updateTask = taskData.updateTask();
                    updateTask.setStatus(TaskStatus.TO_DO);
                    return taskUpdater.update(taskData.getTaskProgressHaute().getId(), updateTask);
                }, throwable -> {
                    assertTrue(throwable instanceof InvalidTaskException);
                });
    }

    @Test
    void shouldNotUpdateStatusFromBlockedToOverdue(UniAsserter asserter) {
        asserter.execute(() -> taskData.init())
                .assertFailedWith(() -> {
                    UpdateTask updateTask = taskData.updateTask();
                    updateTask.setStatus(TaskStatus.OVERDUE);
                    return taskUpdater.update(taskData.getTaskBlockedHaute().getId(), updateTask);
                }, throwable -> {
                    assertTrue(throwable instanceof InvalidTaskException);
                });
    }

    @Test
    void shouldNotUpdateStatusToOverdueWithoutDueDate(UniAsserter asserter) {
        asserter.execute(() -> taskData.init())
                .assertFailedWith(() -> {
                    UpdateTask updateTask = new UpdateTask();
                    updateTask.setStatus(TaskStatus.OVERDUE);
                    return taskUpdater.update(taskData.getTaskTodoNormale().getId(), updateTask);
                }, throwable -> {
                    System.out.println(throwable.getMessage());
                    assertTrue(throwable instanceof InvalidTaskException);
                });
    }
}
