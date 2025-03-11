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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
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
    @JoinTable(name = "task_user", joinColumns = @JoinColumn(name = "task_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<UserEntity> assignedUsers;
    @ManyToOne
    private UserEntity createdBy;
}
