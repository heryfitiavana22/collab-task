import { err, ok, type Result } from "neverthrow";
import type { CreateTask, PaginatedTask, Task, UpdateTask } from "../task";
import type { ITaskService } from "./ITaskService";
import type { HttpError } from "~/lib/http/http-errors";
import { TaskNotFound } from "../exception/TaskNotFound";
import type { IUserService } from "~/user/core/service/IUserService";
import type { UserNotFound } from "~/user/core/exception/UserNotFound";
import type { User } from "~/user/core/user";

export class TaskServiceInMemory implements ITaskService {
  private tasks: Task[] = [];

  constructor(private userService: IUserService) {}

  async findAll(): Promise<Result<PaginatedTask, HttpError>> {
    return ok({
      page: {
        page: 1,
        size: this.tasks.length,
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

  async create(
    createTask: CreateTask
  ): Promise<Result<Task, HttpError | UserNotFound>> {
    const createdByResult = await this.userService.findById(
      createTask.createdByUserId
    );
    if (createdByResult.isErr()) {
      return err(createdByResult.error);
    }

    const assignedUsers: User[] = [];
    for (const userId of createTask.assignedUserIds) {
      const userResult = await this.userService.findById(userId);
      if (userResult.isErr()) {
        return err(userResult.error);
      }
      assignedUsers.push(userResult.value);
    }

    const task: Task = {
      ...createTask,
      id: Math.random().toString(),
      assignedUsers,
      createdAt: new Date(),
      createdBy: createdByResult.value,
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
