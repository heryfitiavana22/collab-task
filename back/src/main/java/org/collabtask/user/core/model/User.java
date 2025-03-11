package org.collabtask.user.core.model;

import java.util.List;

import org.collabtask.task.core.model.Task;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String id;
    private String username;
    private String email;
    private List<Task> tasksAssigned;
    private List<Task> tasksCreated;
}
