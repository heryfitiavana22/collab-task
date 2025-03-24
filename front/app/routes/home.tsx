import type { Route } from "./+types/home";
import TasksPage from "~/task/task-page";

export function meta({}: Route.MetaArgs) {
  return [
    { title: "Gestion des tâches" },
    { name: "collab-task", content: "Welcome to React Router!" },
  ];
}

export default function Home() {
  return <TasksPage />;
}
