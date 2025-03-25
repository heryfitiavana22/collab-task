import { taskService } from "~/task/core/service";
import type { Route } from "./+types/task-update";
import { UpdateTask } from "~/task/update-task";

export function meta({}: Route.MetaArgs) {
  return [
    { title: "Modifier une Tâche" },
    {
      name: "collab-task",
      content: "Moidifer une tâche",
    },
  ];
}

export async function clientLoader({ params }: Route.ClientLoaderArgs) {
  const task = await taskService.findById(params.taskId);
  if (task.isErr()) throw Error("Task not found " + params.taskId);
  return { task: task.value };
}

export default function TaskUpdate({ loaderData }: Route.ComponentProps) {
  const { task } = loaderData;

  return <UpdateTask task={task} />;
}
