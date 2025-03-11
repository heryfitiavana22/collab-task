package org.collabtask.task.core.dto;

import java.time.ZonedDateTime;
import java.util.List;

import org.collabtask.task.core.model.TaskPriority;
import org.collabtask.task.core.model.TaskStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTask {
    private String title;
    private String description;
    private TaskPriority priority;
    private TaskStatus status;
    private ZonedDateTime dueDate;
    private String createdByUserId;
    private List<String> assignedUsersId;
}
