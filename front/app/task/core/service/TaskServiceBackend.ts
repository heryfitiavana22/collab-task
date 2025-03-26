import type { Result } from "neverthrow";
import type { HttpError } from "~/lib/http/http-errors";
import type { UserNotFound } from "~/user/core/exception/UserNotFound";
import type { TaskNotFound } from "../exception/TaskNotFound";
import {
  type PaginatedTask,
  type Task,
  type CreateTask,
  type UpdateTask,
  ArrayTaskSchema,
  TaskSchema,
} from "../task";
import type { ITaskService } from "./ITaskService";
import type { HttpClient } from "~/lib/http/http-client";

export class TaskServiceBackend implements ITaskService {
  constructor(private httpClient: HttpClient) {}

  findAll(): Promise<Result<PaginatedTask, HttpError>> {
    return this.httpClient.get("/", { zodSchema: ArrayTaskSchema });
  }

  findById(id: string): Promise<Result<Task, HttpError | TaskNotFound>> {
    return this.httpClient.get(`/${id}`, { zodSchema: TaskSchema });
  }

  create(
    createTask: CreateTask
  ): Promise<Result<Task, HttpError | UserNotFound>> {
    return this.httpClient.post(`/`, createTask);
  }

  update(
    id: string,
    updatTask: UpdateTask
  ): Promise<Result<Task, HttpError | TaskNotFound>> {
    return this.httpClient.post(`/${id}`, updatTask);
  }
}
