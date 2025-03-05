package org.collabtask.task.database;

import java.time.ZonedDateTime;
import java.util.List;

import org.collabtask.helpers.BaseEntity;
import org.collabtask.task.core.model.TaskPriority;
import org.collabtask.task.core.model.TaskStatus;
import org.collabtask.user.database.UserEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

@Entity(name = "tasks")
public class TaskEntity extends BaseEntity {
    private String title;
    private String description;
    private TaskStatus status;
    private TaskPriority priority;
    private ZonedDateTime dueDate;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
    @ManyToMany
    @JoinTable(
    name = "task_user",
    joinColumns = @JoinColumn(name = "task_id"),
    inverseJoinColumns = @JoinColumn(name = "user_id")
)
    private List<UserEntity> assignedUsers;
    @ManyToOne
    private UserEntity createdBy;

    public TaskEntity() {
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

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public TaskPriority getPriority() {
        return priority;
    }

    public void setPriority(TaskPriority priority) {
        this.priority = priority;
    }

    public ZonedDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(ZonedDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ZonedDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<UserEntity> getAssignedUsers() {
        return assignedUsers;
    }

    public void setAssignedUsers(List<UserEntity> assignedUsers) {
        this.assignedUsers = assignedUsers;
    }

    public UserEntity getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserEntity createdBy) {
        this.createdBy = createdBy;
    }
}
