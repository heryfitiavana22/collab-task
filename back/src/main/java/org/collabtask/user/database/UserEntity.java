package org.collabtask.user.database;

import java.util.List;

import org.collabtask.helpers.BaseEntity;
import org.collabtask.helpers.ConvertibleToClient;
    import org.collabtask.task.database.TaskEntity;
import org.collabtask.user.core.dto.CreateUser;
import org.collabtask.user.core.dto.UserClient;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "users_table")
public class UserEntity extends BaseEntity implements ConvertibleToClient<UserClient> {
    private String username;
    private String email;
    @ManyToMany(mappedBy = "assignedUsers")
    private List<TaskEntity> assignedTasks;
    @OneToMany(mappedBy = "createdBy")
    private List<TaskEntity> createdTasks;

    public UserEntity(String id) {
        this.id = id;
    }

    public UserEntity(CreateUser createUser) {
        this.username = createUser.getUsername();
        this.email = createUser.getEmail();
    }

    public UserClient toClient() {
        return new UserClient(id, username, email);
    }
}
