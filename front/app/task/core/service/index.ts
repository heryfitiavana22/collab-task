import { userService } from "~/user/core/service";
import type { ITaskService } from "./ITaskService";
import { TaskServiceInMemory } from "./TaskServiceInMemory";
import { TaskServiceBackend } from "./TaskServiceBackend";
import { httpClient } from "~/lib/http/http-client";

function factory(source: "InMemory" | "quarkus"): ITaskService {
  if (source == "InMemory") return new TaskServiceInMemory(userService);
  if (source == "quarkus")
    return new TaskServiceBackend(httpClient.withPrefix("/tasks"));
  throw new Error("Unknown source task service");
}

export const taskService = factory("quarkus");
