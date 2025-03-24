import { ok, err, type Result } from "neverthrow";
import type { HttpError } from "~/lib/http/http-errors";
import { UserNotFound } from "../exception/UserNotFound";
import type { PaginatedUser, User, CreateUser } from "../user";
import type { IUserService } from "./IUserService";

export class UserServiceInMemory implements IUserService {
  private users: User[] = [
    { id: "1", username: "john.doe", email: "john.doe@example.com" },
    { id: "2", username: "jane.smith", email: "jane.smith@example.com" },
    { id: "3", username: "bob.johnson", email: "bob.johnson@example.com" },
    {
      id: "4",
      username: "alice.williams",
      email: "alice.williams@example.com",
    },
    { id: "5", username: "charlie.brown", email: "charlie.brown@example.com" },
  ];

  async findAll(): Promise<Result<PaginatedUser, HttpError>> {
    return ok({
      page: {
        page: 1,
        size: this.users.length,
      },
      totalPage: 1,
      data: this.users,
    });
  }

  async findById(id: string): Promise<Result<User, HttpError | UserNotFound>> {
    const user = this.users.find((user) => user.id === id);
    if (!user) {
      return err(new UserNotFound(`User with id ${id} not found`));
    }
    return ok(user);
  }

  async findByEmail(
    email: string
  ): Promise<Result<User, HttpError | UserNotFound>> {
    const user = this.users.find((user) => user.email === email);
    if (!user) {
      return err(new UserNotFound(`User with email ${email} not found`));
    }
    return ok(user);
  }

  async create(createUser: CreateUser): Promise<Result<User, HttpError>> {
    const newUser: User = {
      id: (this.users.length + 1).toString(),
      ...createUser,
    };
    this.users.push(newUser);
    return ok(newUser);
  }

  async deleteById(
    id: string
  ): Promise<Result<User, HttpError | UserNotFound>> {
    const index = this.users.findIndex((user) => user.id === id);
    if (index === -1) {
      return err(new UserNotFound(`User with id ${id} not found`));
    }
    const [deletedUser] = this.users.splice(index, 1);
    return ok(deletedUser);
  }
}
