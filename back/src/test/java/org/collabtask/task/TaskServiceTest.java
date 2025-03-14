package org.collabtask.task;

import org.collabtask.task.core.service.TaskService;
import io.quarkus.test.TestReactiveTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;

@QuarkusTest
@TestReactiveTransaction
public class TaskServiceTest {
    @Inject
    TaskData taskData;

    @Inject
    TaskService taskService;
}
