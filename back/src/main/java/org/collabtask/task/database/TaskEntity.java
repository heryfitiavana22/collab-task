package org.collabtask.task.database;

import java.time.ZonedDateTime;
import java.util.List;

import org.collabtask.helpers.BaseEntity;
import org.collabtask.helpers.ConvertibleToClient;
import org.collabtask.task.core.dto.CreateTask;
import org.collabtask.task.core.dto.TaskClient;
import org.collabtask.task.core.dto.UpdateTask;
import org.collabtask.task.core.model.TaskPriority;
import org.collabtask.task.core.model.TaskStatus;
import org.collabtask.user.database.UserEntity;

import io.smallrye.mutiny.Uni;
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
public class TaskEntity extends BaseEntity implements ConvertibleToClient<TaskClient> {
    private String title;
    private String description;
    private TaskStatus status;
    private TaskPriority priority;
    private ZonedDateTime dueDate;
    @ManyToMany
    @JoinTable(name = "task_user", joinColumns = @JoinColumn(name = "task_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<UserEntity> assignedUsers;
    @ManyToOne
    private UserEntity createdBy;

    public TaskEntity(CreateTask createTask) {
        this.title = createTask.getTitle();
        this.description = createTask.getDescription();
        this.status = createTask.getStatus();
        this.priority = createTask.getPriority();
        this.dueDate = createTask.getDueDate();

        this.createdBy = new UserEntity(createTask.getCreatedByUserId());
        this.assignedUsers = createTask.getAssignedUsersId().stream()
                .map(UserEntity::new)
                .toList();
    }

    public Uni<TaskEntity> updateFrom(UpdateTask updateTask) {
        if (updateTask.getTitle() != null) {
            this.title = updateTask.getTitle();
        }
        if (updateTask.getDescription() != null) {
            this.description = updateTask.getDescription();
        }
        if (updateTask.getStatus() != null) {
            this.status = updateTask.getStatus();
        }
        if (updateTask.getPriority() != null) {
            this.priority = updateTask.getPriority();
        }
        if (updateTask.getDueDate() != null) {
            this.dueDate = updateTask.getDueDate();
        }
        return this.persist();
    }

    public TaskClient toClient() {
        return new TaskClient(id, title, description, status, priority, dueDate, createdAt, updatedAt,
                assignedUsers.stream().map(el -> el.toClient()).toList(),
                createdBy.toClient());
    }
}
