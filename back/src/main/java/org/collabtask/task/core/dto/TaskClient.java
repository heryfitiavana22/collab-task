package org.collabtask.task.core.dto;

import java.time.ZonedDateTime;
import java.util.List;

import org.collabtask.task.core.model.TaskPriority;
import org.collabtask.task.core.model.TaskStatus;
import org.collabtask.user.core.dto.UserClient;

public class TaskClient {
    private String id;
    private String title;
    private String description;
    private TaskStatus status;
    private TaskPriority priority;
    private ZonedDateTime dueDate;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
    private List<UserClient> assignedUsers;
    private UserClient createdBy;

    public TaskClient() {
    }

    public TaskClient(String id, String title, String description, TaskStatus status, TaskPriority priority, ZonedDateTime dueDate, ZonedDateTime createdAt, ZonedDateTime updatedAt, List<UserClient> assignedUsers, UserClient createdBy) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.dueDate = dueDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.assignedUsers = assignedUsers;
        this.createdBy = createdBy;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public List<UserClient> getAssignedUsers() {
        return assignedUsers;
    }

    public void setAssignedUsers(List<UserClient> assignedUsers) {
        this.assignedUsers = assignedUsers;
    }

    public UserClient getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserClient createdBy) {
        this.createdBy = createdBy;
    }
}
