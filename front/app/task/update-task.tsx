import { Link } from "react-router";
import type { Task } from "./core/task";
import { Button } from "~/components/ui/button";
import { ArrowLeftIcon } from "lucide-react";
import TaskForm from "./components/task-form";

export function UpdateTask({ task }: UpdateTaskProps) {
  return (
    <div className="container py-6 space-y-6">
      <div className="flex items-center gap-4">
        <Link to="/">
          <Button variant="outline" size="icon">
            <ArrowLeftIcon className="h-4 w-4" />
          </Button>
        </Link>
        <h1 className="text-3xl font-bold tracking-tight">Modifier la tâche</h1>
      </div>

      <TaskForm task={task} />
    </div>
  );
}

type UpdateTaskProps = { task: Task };
