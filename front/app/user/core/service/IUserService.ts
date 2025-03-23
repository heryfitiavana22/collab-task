import type { Result } from "neverthrow";
import type { CreateUser, PaginatedUser, User } from "../user";

export interface IUserService {
  findAll(): Promise<Result<PaginatedUser, Error>>;
  findById(id: string): Promise<Result<User, Error>>;
  findByEmail(email: string): Promise<Result<User, Error>>;
  create(createUser: CreateUser): Promise<Result<User, Error>>;
  deleteById(id: string): Promise<Result<User, Error>>;
}
