package org.collabtask.task;

import java.util.ArrayList;
import java.util.List;

import org.collabtask.helpers.ZonedDateTimeHelper;
import org.collabtask.task.core.dto.CreateTask;
import org.collabtask.task.core.dto.UpdateTask;
import org.collabtask.task.core.model.TaskPriority;
import org.collabtask.task.core.model.TaskStatus;
import org.collabtask.task.database.TaskEntity;
import org.collabtask.user.UserData;
import org.collabtask.user.database.UserEntity;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.Getter;

@Getter
@ApplicationScoped
public class TaskData {
    TaskEntity taskTodoNormale;
    TaskEntity taskProgressHaute;

    @Inject
    UserData userData;

    public Uni<String> init() {
        return userData.init()
                .chain(() -> {
                    List<UserEntity> usersTaskTodoNormale = new ArrayList<>();
                    usersTaskTodoNormale.add(userData.getUserEntity2());

                    taskTodoNormale = new TaskEntity("tasktodonormale", "descri", TaskStatus.TO_DO,
                            TaskPriority.normale,
                            ZonedDateTimeHelper.now().plusDays(1),
                            usersTaskTodoNormale, userData.getUserEntity1());

                    List<UserEntity> userstaskProgressHaute = new ArrayList<>();
                    userstaskProgressHaute.add(userData.getUserEntity2());

                    taskProgressHaute = new TaskEntity("taskProgressHaute", "descri", TaskStatus.IN_PROGRESS,
                            TaskPriority.haute,
                            ZonedDateTimeHelper.now().plusDays(1),
                            userstaskProgressHaute, userData.getUserEntity1());

                    return taskTodoNormale.persistAndFlush()
                            .chain(() -> taskProgressHaute.persistAndFlush());
                })
                .replaceWith("go");
    }

    public CreateTask createTask() {
        List<String> assignedUsersId = new ArrayList<>();
        assignedUsersId.add(userData.getUserEntity2().getId());
        return new CreateTask("createTask", "des", TaskPriority.basse, TaskStatus.TO_DO,
                ZonedDateTimeHelper.now().plusDays(2), userData.getUserEntity1().getId(), assignedUsersId);
    }

    public UpdateTask updateTask() {
        return new UpdateTask("UpdateTask", "des", TaskPriority.basse, TaskStatus.TO_DO,
                ZonedDateTimeHelper.now().plusDays(2), userData.getUserEntity1().getId());
    }

    public UpdateTask updateTaskPartial() {
        UpdateTask update = new UpdateTask();
        update.setTitle("update title only");
        return update;
    }
}
