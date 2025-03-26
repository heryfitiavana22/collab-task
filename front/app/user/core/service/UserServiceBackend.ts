import type { Result } from "neverthrow";
import type { HttpError } from "~/lib/http/http-errors";
import type { UserNotFound } from "../exception/UserNotFound";
import {
  type PaginatedUser,
  type User,
  type CreateUser,
  ArrayUserSchema,
  UserSchema,
} from "../user";
import type { IUserService } from "./IUserService";
import type { HttpClient } from "~/lib/http/http-client";

export class UserServiceBackend implements IUserService {
  constructor(private httpClient: HttpClient) {}

  findAll(): Promise<Result<PaginatedUser, HttpError>> {
    return this.httpClient.get("/", { zodSchema: ArrayUserSchema });
  }

  findById(id: string): Promise<Result<User, HttpError | UserNotFound>> {
    return this.httpClient.get(`/${id}`, { zodSchema: UserSchema });
  }

  findByEmail(email: string): Promise<Result<User, HttpError | UserNotFound>> {
    return this.httpClient.get(`/email/${email}`, { zodSchema: UserSchema });
  }

  create(createUser: CreateUser): Promise<Result<User, HttpError>> {
    return this.httpClient.post(`/`, createUser);
  }

  deleteById(id: string): Promise<Result<User, HttpError | UserNotFound>> {
    return this.httpClient.delete(`/${id}`);
  }
}
