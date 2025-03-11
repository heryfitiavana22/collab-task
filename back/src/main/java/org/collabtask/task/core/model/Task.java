package org.collabtask.task.core.model;

import java.time.ZonedDateTime;
import java.util.List;

import org.collabtask.user.core.model.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    private String id;
    private String title;
    private String description;
    private TaskStatus status;
    private TaskPriority priority;
    private ZonedDateTime dueDate;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
    private List<User> assignedUsers;
    private User createdBy;
}
