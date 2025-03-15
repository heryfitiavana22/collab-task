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
        TaskEntity taskOverdueNormale;
        TaskEntity taskProgressHaute;
        TaskEntity taskCompletedHaute;

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

                                        taskProgressHaute = new TaskEntity("taskProgressHaute", "descri",
                                                        TaskStatus.IN_PROGRESS,
                                                        TaskPriority.haute,
                                                        ZonedDateTimeHelper.now().plusDays(1),
                                                        userstaskProgressHaute, userData.getUserEntity1());

                                        List<UserEntity> userstaskOverdueNormal = new ArrayList<>();
                                        userstaskOverdueNormal.add(userData.getUserEntity2());

                                        taskOverdueNormale = new TaskEntity("taskOverdueNormale", "descri",
                                                        TaskStatus.IN_PROGRESS,
                                                        TaskPriority.haute,
                                                        ZonedDateTimeHelper.now().plusDays(1),
                                                        userstaskOverdueNormal, userData.getUserEntity1());

                                        List<UserEntity> usersTaskCompletedHaute = new ArrayList<>();
                                        usersTaskCompletedHaute.add(userData.getUserEntity2());

                                        taskCompletedHaute = new TaskEntity("taskCompletedHaute", "descri",
                                                        TaskStatus.COMPLETED,
                                                        TaskPriority.haute,
                                                        ZonedDateTimeHelper.now().minusDays(1),
                                                        userstaskOverdueNormal, userData.getUserEntity1());

                                        return taskTodoNormale.persistAndFlush()
                                                        .chain(() -> taskProgressHaute.persistAndFlush())
                                                        .chain(() -> taskCompletedHaute.persistAndFlush())
                                                        .chain(() -> taskOverdueNormale.persistAndFlush());
                                })
                                .replaceWith("go");
        }

        public CreateTask createTask() {
                List<String> assignedUsersId = new ArrayList<>();
                assignedUsersId.add(userData.getUserEntity2().getId());
                return new CreateTask("createTask", "des", TaskPriority.basse, TaskStatus.TO_DO,
                                ZonedDateTimeHelper.now().plusDays(2), userData.getUserEntity1().getId(),
                                assignedUsersId);
        }

        public UpdateTask updateTask() {
                return new UpdateTask("UpdateTask", "des", TaskPriority.basse, TaskStatus.TO_DO,
                                ZonedDateTimeHelper.now().plusDays(2));
        }

        public UpdateTask updateTaskPartial() {
                UpdateTask update = new UpdateTask();
                update.setTitle("update title only");
                return update;
        }
}
