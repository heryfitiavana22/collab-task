import { err, ok, type Result } from "neverthrow";
import type { CreateTask, PaginatedTask, Task, UpdateTask } from "../task";
import type { ITaskService } from "./ITaskService";
import type { HttpError } from "~/lib/http/http-errors";
import { TaskNotFound } from "../exception/TaskNotFound";

export class TaskServiceInMemory implements ITaskService {
  private tasks: Task[] = [];

  async findAll(): Promise<Result<PaginatedTask, HttpError>> {
    return ok({
      page: {
        page: 1,
        size: 10,
      },
      totalPage: 1,
      data: this.tasks,
    });
  }

  async findById(id: string): Promise<Result<Task, HttpError | TaskNotFound>> {
    const task = this.tasks.find((task) => task.id === id);
    if (!task) {
      return err(new TaskNotFound(`Task with id ${id} not found`));
    }
    return ok(task);
  }

  async create(createTask: CreateTask): Promise<Result<Task, HttpError>> {
    const task: Task = {
      ...createTask,
      id: Math.random().toString(),
      assignedUsers: [],
      createdAt: new Date(),
      createdBy: {
        id: "1",
        email: "1",
        username: "1",
      },
      updatedAt: new Date(),
    };
    this.tasks.push(task);
    return ok(task);
  }

  async update(
    id: string,
    updateTask: UpdateTask
  ): Promise<Result<Task, HttpError | TaskNotFound>> {
    const taskIndex = this.tasks.findIndex((task) => task.id === id);
    if (taskIndex === -1) {
      return err(new TaskNotFound(`Task with id ${id} not found`));
    }
    this.tasks[taskIndex] = {
      ...this.tasks[taskIndex],
      ...updateTask,
    };
    return ok(this.tasks[taskIndex]);
  }
}
