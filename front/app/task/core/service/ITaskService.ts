import type { CreateTask, PaginatedTask, Task, UpdateTask } from "../task";
import type { Result } from "neverthrow";

export interface ITaskService {
  findAll(): Promise<Result<PaginatedTask, Error>>;
  findById(id: string): Promise<Result<Task, Error>>;
  create(createTask: CreateTask): Promise<Result<Task, Error>>;
  update(updatTask: UpdateTask): Promise<Result<Task, Error>>;
}
