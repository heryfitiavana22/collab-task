package org.collabtask.task.database;

import java.util.List;

import org.collabtask.helpers.FindByUserIdPagination;
import org.collabtask.helpers.PaginatedResponse;
import org.collabtask.helpers.Pagination;
import org.collabtask.task.core.contracts.ITaskRepository;
import org.collabtask.task.core.dto.CreateTask;
import org.collabtask.task.core.dto.TaskClient;
import org.collabtask.task.core.dto.UpdateTask;
import org.collabtask.task.core.exception.TaskNotFoundException;
import org.collabtask.task.core.model.TaskPriority;
import org.collabtask.task.core.model.TaskStatus;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TaskEntityRepository implements ITaskRepository {

    @Override
    public Uni<PaginatedResponse<TaskClient>> findAll(Pagination pagination) {
        Uni<List<TaskEntity>> find = TaskEntity.findAll()
                .page(pagination.getPage(), pagination.getSize())
                .list();
        Uni<Long> total = TaskEntity.count();

        return PaginatedResponse.paginate(find, total, pagination);
    }

    @Override
    public Uni<TaskClient> create(CreateTask createTask) {
        TaskEntity entity = new TaskEntity(createTask);
        Uni<TaskEntity> created = entity.persist();
        return created.map(el -> el.toClient());
    }

    @Override
    public Uni<TaskClient> update(String id, UpdateTask updateTask) throws TaskNotFoundException {
        Uni<TaskEntity> find = TaskEntity.findById(id);
        return find.onItem().transformToUni(entity -> {
            if (entity == null)
                return Uni.createFrom().failure(() -> new TaskNotFoundException(id));
            return entity.updateFrom(updateTask).map(el -> el.toClient());
        });
    }

    @Override
    public Uni<TaskClient> findById(String id) throws TaskNotFoundException {
        Uni<TaskEntity> find = TaskEntity.findById(id);
        return find.onItem().transformToUni(entity -> {
            if (entity == null)
                return Uni.createFrom().failure(() -> new TaskNotFoundException(id));
            return Uni.createFrom().item(entity.toClient());
        });
    }

    @Override
    public Uni<PaginatedResponse<TaskClient>> findByCreatedByUserId(FindByUserIdPagination findByUserIdPagination) {
        Uni<List<TaskEntity>> find = TaskEntity.find("createdBy.id = ?1", findByUserIdPagination.getUserId())
                .page(findByUserIdPagination.getPage(), findByUserIdPagination.getSize())
                .list();
        Uni<Long> total = TaskEntity.count("createdBy.id = ?1", findByUserIdPagination.getUserId());

        return PaginatedResponse.paginate(find, total, findByUserIdPagination);
    }

    @Override
    public Uni<PaginatedResponse<TaskClient>> findByAssignedUsersId(String userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByAssignedUsersId'");
    }

    @Override
    public Uni<PaginatedResponse<TaskClient>> findByStatus(TaskStatus status) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByStatus'");
    }

    @Override
    public Uni<PaginatedResponse<TaskClient>> findByPriorityOrderByDueDate(TaskPriority priority) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByPriorityOrderByDueDate'");
    }

    @Override
    public Uni<PaginatedResponse<TaskClient>> findLate() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findLate'");
    }

    @Override
    public Uni<PaginatedResponse<TaskClient>> findUrgent() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findUrgent'");
    }

}
