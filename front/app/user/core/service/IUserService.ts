import type { Result } from "neverthrow";
import type { CreateUser, PaginatedUser, User } from "../user";
import type { HttpError } from "~/lib/http/http-errors";
import type { UserNotFound } from "../exception/UserNotFound";

export interface IUserService {
  findAll(): Promise<Result<PaginatedUser, HttpError>>;
  findById(id: string): Promise<Result<User, HttpError | UserNotFound>>;
  findByEmail(email: string): Promise<Result<User, HttpError | UserNotFound>>;
  create(createUser: CreateUser): Promise<Result<User, HttpError>>;
  deleteById(id: string): Promise<Result<User, HttpError | UserNotFound>>;
}
