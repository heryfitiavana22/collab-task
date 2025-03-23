import { z } from "zod";
import { withPagination } from "~/lib/pagination";

export const UserSchema = z.object({
  id: z.string(),
  username: z.string(),
  email: z.string(),
});

export const CreateUserSchema = z.object({
  username: z.string(),
  email: z.string(),
});

export const ArrayUserSchema = z.array(UserSchema);

export const PaginatedUserSchema = withPagination(ArrayUserSchema);

export type User = z.infer<typeof UserSchema>;
export type PaginatedUser = z.infer<typeof PaginatedUserSchema>;
export type CreateUser = z.infer<typeof CreateUserSchema>;
