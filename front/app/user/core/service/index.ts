import type { IUserService } from "./IUserService";
import { UserServiceInMemory } from "./UserServiceInMemory";

function factory(src: "InMemory" | "quarkus"): IUserService {
  if (src == "InMemory") return new UserServiceInMemory();
  throw new Error("Unknown source user service");
}

export const userService = factory("InMemory");
