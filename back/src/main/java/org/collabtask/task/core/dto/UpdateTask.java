package org.collabtask.task.core.dto;

import java.time.ZonedDateTime;

import org.collabtask.task.core.model.TaskPriority;
import org.collabtask.task.core.model.TaskStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTask {
    private String title;
    private String description;
    private TaskPriority priority;
    private TaskStatus status;
    private ZonedDateTime dueDate;
}
