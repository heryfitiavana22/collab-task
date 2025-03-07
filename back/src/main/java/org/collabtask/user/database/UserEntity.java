package org.collabtask.user.database;

import java.util.List;

import org.collabtask.helpers.BaseEntity;
import org.collabtask.task.database.TaskEntity;
import org.collabtask.user.core.dto.CreateUser;
import org.collabtask.user.core.dto.UserClient;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

@Entity(name = "users_table")
public class UserEntity extends BaseEntity {
    private String username;
    private String email;
    @ManyToMany(mappedBy = "assignedUsers")
    private List<TaskEntity> assignedTasks;
    @OneToMany(mappedBy = "createdBy")
    private List<TaskEntity> createdTasks;

    public UserEntity() {
    }

    public UserEntity(CreateUser createUser) {
        this.username = createUser.getUsername();
        this.email = createUser.getEmail();
    }

    public UserClient toClient() {
        return new UserClient(id, username, email);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<TaskEntity> getAssignedTasks() {
        return assignedTasks;
    }

    public void setAssignedTasks(List<TaskEntity> assignedTasks) {
        this.assignedTasks = assignedTasks;
    }

    public List<TaskEntity> getCreatedTasks() {
        return createdTasks;
    }

    public void setCreatedTasks(List<TaskEntity> createdTasks) {
        this.createdTasks = createdTasks;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", assignedTasks=" + assignedTasks +
                ", createdTasks=" + createdTasks +
                '}';
    }
}
