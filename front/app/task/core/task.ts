import { z } from "zod";
import { withPagination } from "~/lib/pagination";
import { UserSchema } from "~/user/core/user";

export enum TaskStatus {
  TO_DO = "TO_DO",
  IN_PROGRESS = "IN_PROGRESS",
  BLOCKED = "BLOCKED",
  COMPLETED = "COMPLETED",
  OVERDUE = "OVERDUE",
}

export enum TaskPriority {
  basse = "basse",
  normale = "normale",
  haute = "haute",
  critique = "critique",
}

export const TaskSchema = z.object({
  id: z.string(),
  title: z.string(),
  description: z.string(),
  status: z.nativeEnum(TaskStatus),
  priority: z.nativeEnum(TaskPriority),
  dueDate: z.coerce.date(),
  createdAt: z.coerce.date(),
  updatedAt: z.coerce.date(),
  assignedUsers: z.array(UserSchema),
  createdBy: UserSchema,
});

export const ArrayTaskSchema = z.array(TaskSchema);

export const PaginatedTaskSchema = withPagination(ArrayTaskSchema);

export const CreateTaskSchema = z.object({
  title: z.string(),
  description: z.string(),
  status: z.nativeEnum(TaskStatus),
  priority: z.nativeEnum(TaskPriority),
  dueDate: z.coerce.date(),
  createdByUserId: z.string(),
  assignedUserIds: z.array(z.string()),
});

export const UpdateTaskSchema = z
  .object({
    title: z.string(),
    description: z.string(),
    status: z.nativeEnum(TaskStatus),
    priority: z.nativeEnum(TaskPriority),
    dueDate: z.coerce.date(),
  })
  .partial();

export type Task = z.infer<typeof TaskSchema>;
export type PaginatedTask = z.infer<typeof PaginatedTaskSchema>;
export type CreateTask = z.infer<typeof CreateTaskSchema>;
export type UpdateTask = z.infer<typeof UpdateTaskSchema>;
