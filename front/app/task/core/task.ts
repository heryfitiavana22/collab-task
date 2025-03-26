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

export const TaskSchemaBase = z.object({
  title: z.string().min(1, "Le titre est requis"),
  description: z.string().min(1, "La description est requis"),
  status: z.nativeEnum(TaskStatus),
  priority: z.nativeEnum(TaskPriority),
  dueDate: z.coerce.date({
    required_error: "La date d'échéance est requise",
  }),
});

export const TaskSchema = TaskSchemaBase.merge(
  z.object({
    id: z.string(),
    createdAt: z.coerce.date(),
    updatedAt: z.coerce.date(),
    assignedUsers: z.array(UserSchema),
    createdBy: UserSchema,
  })
);

export const ArrayTaskSchema = z.array(TaskSchema);

export const PaginatedTaskSchema = withPagination(ArrayTaskSchema);

export const CreateTaskSchema = TaskSchemaBase.merge(
  z.object({
    createdByUserId: z.string().min(1, "Le créateur est requis"),
    assignedUserIds: z.array(z.string()).min(1, "Au moins un utilisateur doit être assigné"),
  })
);

export const UpdateTaskSchema = TaskSchemaBase.partial();

export type Task = z.infer<typeof TaskSchema>;
export type PaginatedTask = z.infer<typeof PaginatedTaskSchema>;
export type CreateTask = z.infer<typeof CreateTaskSchema>;
export type UpdateTask = z.infer<typeof UpdateTaskSchema>;
