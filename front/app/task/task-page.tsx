import { Suspense } from "react";
import { TaskList } from "./components/task-list";
import { TaskListSkeleton } from "./components/task-list-skeleton";
import { Button } from "~/components/ui/button";
import { PlusIcon } from "lucide-react";
import { Link } from "react-router";

export default function TasksPage() {
  return (
    <>
      <div className="flex items-center justify-between">
        <h1 className="text-3xl font-bold tracking-tight">
          Gestion des tâches
        </h1>
        <Link to="/tasks/new">
          <Button>
            <PlusIcon className="mr-2 h-4 w-4" />
            Nouvelle tâche
          </Button>
        </Link>
      </div>

      <Suspense fallback={<TaskListSkeleton />}>
        <TaskList />
      </Suspense>
    </>
  );
}
