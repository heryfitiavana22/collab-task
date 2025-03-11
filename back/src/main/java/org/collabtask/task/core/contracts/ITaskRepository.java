package org.collabtask.task.core.contracts;

import java.time.ZonedDateTime;
import java.util.List;

import org.collabtask.helpers.FindByUserIdPagination;
import org.collabtask.task.core.model.Task;
import org.collabtask.task.core.model.TaskPriority;
import org.collabtask.task.core.model.TaskStatus;
import org.collabtask.task.database.TaskEntity;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

public interface ITaskRepository {
    Multi<Task> findAll(FindByUserIdPagination findByUserIdPagination);

    Multi<Task> findByAssignedUsersId(String userId);

    Multi<Task> findByCreatedById(String userId);

    Multi<Task> findByStatus(TaskStatus status);

    Multi<Task> findByPriorityOrderByDueDate(TaskPriority priority);

    Multi<Task> findLateTasks();

    Multi<Task> findUrgentTasks();

    Uni<TaskEntity> createTask(TaskCreationRepo request);

    public class TaskCreationRepo {
        private String title;
        private String description;
        private TaskPriority priority;
        private TaskStatus status;
        private ZonedDateTime dueDate;
        private String createdByUserId;
        private List<String> assignedUsersId;

        public TaskCreationRepo() {
        }

        public TaskCreationRepo(String title, String description, TaskPriority priority, TaskStatus status,
                ZonedDateTime dueDate, String createdByUserId, List<String> assignedUsersId) {
            this.title = title;
            this.description = description;
            this.priority = priority;
            this.status = status;
            this.dueDate = dueDate;
            this.createdByUserId = createdByUserId;
            this.assignedUsersId = assignedUsersId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public TaskPriority getPriority() {
            return priority;
        }

        public void setPriority(TaskPriority priority) {
            this.priority = priority;
        }

        public TaskStatus getStatus() {
            return status;
        }

        public void setStatus(TaskStatus status) {
            this.status = status;
        }

        public ZonedDateTime getDueDate() {
            return dueDate;
        }

        public void setDueDate(ZonedDateTime dueDate) {
            this.dueDate = dueDate;
        }

        public String getCreatedByUserId() {
            return createdByUserId;
        }

        public void setCreatedByUserId(String createdByUserId) {
            this.createdByUserId = createdByUserId;
        }

        public List<String> getAssignedUsersId() {
            return assignedUsersId;
        }

        public void setAssignedUsersId(List<String> assignedUsersId) {
            this.assignedUsersId = assignedUsersId;
        }

    }
}
