import { userService } from "~/user/core/service";
import type { ITaskService } from "./ITaskService";
import { TaskServiceInMemory } from "./TaskServiceInMemory";

function factory(source: "InMemory" | "quarkus"): ITaskService {
  if (source == "InMemory") return new TaskServiceInMemory(userService);
  throw new Error("Unknown source task service");
}

export const taskService = factory("InMemory");
