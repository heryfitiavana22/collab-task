package org.collabtask.task.core.dto;

import java.time.ZonedDateTime;
import java.util.List;

import org.collabtask.task.core.model.TaskPriority;
import org.collabtask.task.core.model.TaskStatus;
import org.collabtask.user.core.dto.UserClient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
}
