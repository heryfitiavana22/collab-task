import { Button } from "~/components/ui/button";
import { ArrowLeftIcon } from "lucide-react";
import { Link } from "react-router";
import TaskForm from "./components/task-form";

export function CreateTask() {
  return (
    <>
      <div className="flex items-center gap-4">
        <Link to="/">
          <Button variant="outline" size="icon">
            <ArrowLeftIcon className="h-4 w-4" />
          </Button>
        </Link>
        <h1 className="text-3xl font-bold tracking-tight">
          Créer une nouvelle tâche
        </h1>
      </div>

      <TaskForm />
    </>
  );
}
