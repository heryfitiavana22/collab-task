import type { HttpError } from "~/lib/http/http-errors";
import type { CreateTask, PaginatedTask, Task, UpdateTask } from "../task";
import type { Result } from "neverthrow";
import type { TaskNotFound } from "../exception/TaskNotFound";
import type { UserNotFound } from "~/user/core/exception/UserNotFound";

export interface ITaskService {
  findAll(): Promise<Result<PaginatedTask, HttpError>>;
  findById(id: string): Promise<Result<Task, HttpError | TaskNotFound>>;
  create(
    createTask: CreateTask
  ): Promise<Result<Task, HttpError | UserNotFound>>;
  update(
    id: string,
    updatTask: UpdateTask
  ): Promise<Result<Task, HttpError | TaskNotFound>>;
}
