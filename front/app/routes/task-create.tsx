import { CreateTask } from "~/task/create-task";
import type { Route } from "./+types/task-create";

export function meta({}: Route.MetaArgs) {
return [
    { title: "Créer une Nouvelle Tâche" },
    { name: "collab-task", content: "Créer une nouvelle tâche." },
];
}

export default function TaskCreate() {
  return <CreateTask />;
}
