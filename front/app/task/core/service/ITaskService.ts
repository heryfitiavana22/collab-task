import type { HttpError } from "~/lib/http/http-errors";
import type { CreateTask, PaginatedTask, Task, UpdateTask } from "../task";
import type { Result } from "neverthrow";

export interface ITaskService {
  findAll(): Promise<Result<PaginatedTask, HttpError>>;
  findById(id: string): Promise<Result<Task, HttpError>>;
  create(createTask: CreateTask): Promise<Result<Task, HttpError>>;
  update(updatTask: UpdateTask): Promise<Result<Task, HttpError>>;
}
