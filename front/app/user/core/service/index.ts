import { httpClient } from "~/lib/http/http-client";
import type { IUserService } from "./IUserService";
import { UserServiceBackend } from "./UserServiceBackend";
import { UserServiceInMemory } from "./UserServiceInMemory";

function factory(src: "InMemory" | "quarkus"): IUserService {
  if (src == "InMemory") return new UserServiceInMemory();
  if (src == "quarkus")
    return new UserServiceBackend(httpClient.withPrefix("/users"));
  throw new Error("Unknown source user service");
}

export const userService = factory("quarkus");
