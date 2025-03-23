import type { ResultAsync } from "neverthrow";
import type { CreateUser, PaginatedUser, User } from "../user";

export interface IUserService {
  findAll(): ResultAsync<PaginatedUser, Error>;
  findById(id: string): ResultAsync<User, Error>;
  findByEmail(email: string): ResultAsync<User, Error>;
  create(createUser: CreateUser): ResultAsync<User, Error>;
  deleteById(id: string): ResultAsync<User, Error>;
}
