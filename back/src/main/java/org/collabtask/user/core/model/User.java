package org.collabtask.user.core.model;

import java.util.List;

import org.collabtask.task.core.model.Task;

public class User {
    private String id;
    private String username;
    private String email;
    private List<Task> tasksAssigned;
    private List<Task> tasksCreated;

    public User(String id, String username, String email, List<Task> tasksAssigned, List<Task> tasksCreated) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.tasksAssigned = tasksAssigned;
        this.tasksCreated = tasksCreated;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public List<Task> getTasksAssigned() {
        return tasksAssigned;
    }

    public void setTasksAssigned(List<Task> tasksAssigned) {
        this.tasksAssigned = tasksAssigned;
    }

    public List<Task> getTasksCreated() {
        return tasksCreated;
    }

    public void setTasksCreated(List<Task> tasksCreated) {
        this.tasksCreated = tasksCreated;
    }
}
