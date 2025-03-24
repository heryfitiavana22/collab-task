import { useState } from "react";
import { format } from "date-fns";
import { fr } from "date-fns/locale";
import {
  Card,
  CardContent,
  CardFooter,
  CardHeader,
  CardTitle,
} from "~/components/ui/card";
import { Badge } from "~/components/ui/badge";
import { Button } from "~/components/ui/button";
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuTrigger,
} from "~/components/ui/dropdown-menu";
import {
  Tooltip,
  TooltipContent,
  TooltipProvider,
  TooltipTrigger,
} from "~/components/ui/tooltip";
import { Avatar, AvatarFallback } from "~/components/ui/avatar";
// import { deleteTask } from "~/lib/tasks";
import {
  CalendarIcon,
  Clock,
  MoreVertical,
  Pencil,
  Trash2,
  AlertTriangle,
  CheckCircle2,
  Clock3,
  XCircle,
  HourglassIcon,
} from "lucide-react";
import { TaskPriority, TaskStatus, type Task } from "../core/task";
import { Link } from "react-router";
import { toast } from "sonner";
import { ConfirmDialog } from "~/components/confirm-dialog";

interface TaskCardProps {
  task: Task;
}

export function TaskCard({ task }: TaskCardProps) {
  const [isDeleting, setIsDeleting] = useState(false);

  const handleDelete = async () => {
    setIsDeleting(true);
    try {
      //
      toast("Tâche supprimée", {
        description: "La tâche a été supprimée avec succès.",
      });
      // Force a refresh of the current page
      window.location.reload();
    } catch (error) {
      toast("Erreur", {
        description:
          "Une erreur est survenue lors de la suppression de la tâche.",
      });
    } finally {
      setIsDeleting(false);
    }
  };

  const isDueSoon = () => {
    const dueDate = new Date(task.dueDate);
    const today = new Date();
    const diffTime = dueDate.getTime() - today.getTime();
    const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24));
    return (
      diffDays <= 2 && diffDays >= 0 && task.status !== TaskStatus.COMPLETED
    );
  };

  const isOverdue = () => {
    const dueDate = new Date(task.dueDate);
    const today = new Date();
    return dueDate < today && task.status !== TaskStatus.COMPLETED;
  };

  return (
    <Card className="overflow-hidden transition-all hover:shadow-md">
      <CardHeader className="pb-2">
        <div className="flex justify-between items-start">
          <CardTitle className="text-lg font-semibold line-clamp-2">
            {task.title}
          </CardTitle>
          <DropdownMenu>
            <DropdownMenuTrigger asChild>
              <Button variant="ghost" size="icon" className="h-8 w-8">
                <MoreVertical className="h-4 w-4" />
              </Button>
            </DropdownMenuTrigger>
            <DropdownMenuContent align="end">
              <DropdownMenuItem asChild>
                <Link to={`/tasks/${task.id}`}>
                  <Pencil className="mr-2 h-4 w-4" />
                  Modifier
                </Link>
              </DropdownMenuItem>
              <DropdownMenuItem className="text-destructive focus:text-destructive">
                <ConfirmDialog
                  description="Cette action ne peut pas être annulée. Cette tâche sera définitivement supprimé"
                  onConfirm={handleDelete}
                  disableButton={isDeleting}
                >
                  <Trash2 className="mr-2 h-4 w-4" />
                  Supprimer
                </ConfirmDialog>
              </DropdownMenuItem>
            </DropdownMenuContent>
          </DropdownMenu>
        </div>
        <div className="flex flex-wrap gap-2 mt-2">
          {getStatusBadge(task.status)}
          {getPriorityBadge(task.priority)}
        </div>
      </CardHeader>
      <CardContent className="pb-2">
        <p className="text-sm text-muted-foreground line-clamp-3 min-h-[3rem]">
          {task.description || "Aucune description"}
        </p>

        <div className="flex items-center mt-4 text-sm text-muted-foreground">
          <CalendarIcon className="mr-1 h-4 w-4" />
          <span
            className={`${
              isOverdue()
                ? "text-destructive"
                : isDueSoon()
                ? "text-amber-600"
                : ""
            }`}
          >
            {format(new Date(task.dueDate), "d MMMM yyyy", { locale: fr })}
            {isDueSoon() && !isOverdue() && " (Bientôt)"}
            {isOverdue() && " (En retard)"}
          </span>
        </div>
      </CardContent>
      <CardFooter className="pt-2 flex justify-between items-center">
        <div className="flex -space-x-2">
          {task.assignedUsers.slice(0, 3).map((user) => (
            <TooltipProvider key={user.id}>
              <Tooltip>
                <TooltipTrigger asChild>
                  <Avatar className="h-7 w-7 border-2 border-background">
                    <AvatarFallback className="text-xs">
                      {user.username.substring(0, 2).toUpperCase()}
                    </AvatarFallback>
                  </Avatar>
                </TooltipTrigger>
                <TooltipContent>
                  <p>{user.username}</p>
                </TooltipContent>
              </Tooltip>
            </TooltipProvider>
          ))}
          {task.assignedUsers.length > 3 && (
            <TooltipProvider>
              <Tooltip>
                <TooltipTrigger asChild>
                  <Avatar className="h-7 w-7 border-2 border-background">
                    <AvatarFallback className="text-xs">
                      +{task.assignedUsers.length - 3}
                    </AvatarFallback>
                  </Avatar>
                </TooltipTrigger>
                <TooltipContent>
                  <p>
                    {task.assignedUsers.length - 3} utilisateurs supplémentaires
                  </p>
                </TooltipContent>
              </Tooltip>
            </TooltipProvider>
          )}
        </div>
        <div className="text-xs text-muted-foreground flex items-center">
          <Clock className="mr-1 h-3 w-3" />
          {format(new Date(task.createdAt), "d MMM yyyy", { locale: fr })}
        </div>
      </CardFooter>
    </Card>
  );
}

const getStatusBadge = (status: TaskStatus) => {
  switch (status) {
    case TaskStatus.TO_DO:
      return (
        <Badge variant="outline" className="flex items-center gap-1">
          <Clock3 className="h-3 w-3" /> À faire
        </Badge>
      );
    case TaskStatus.IN_PROGRESS:
      return (
        <Badge variant="secondary" className="flex items-center gap-1">
          <HourglassIcon className="h-3 w-3" /> En cours
        </Badge>
      );
    case TaskStatus.BLOCKED:
      return (
        <Badge variant="destructive" className="flex items-center gap-1">
          <XCircle className="h-3 w-3" /> Bloquée
        </Badge>
      );
    case TaskStatus.COMPLETED:
      return (
        <Badge
          variant="success"
          className="flex items-center gap-1 bg-green-100 text-green-800 hover:bg-green-200"
        >
          <CheckCircle2 className="h-3 w-3" /> Terminée
        </Badge>
      );
    case TaskStatus.OVERDUE:
      return (
        <Badge variant="destructive" className="flex items-center gap-1">
          <AlertTriangle className="h-3 w-3" /> En retard
        </Badge>
      );
    default:
      return <Badge variant="outline">{status}</Badge>;
  }
};

const getPriorityBadge = (priority: TaskPriority) => {
  switch (priority) {
    case TaskPriority.basse:
      return (
        <Badge
          variant="outline"
          className="bg-blue-50 text-blue-700 hover:bg-blue-100 border-blue-200"
        >
          Basse
        </Badge>
      );
    case TaskPriority.normale:
      return (
        <Badge
          variant="outline"
          className="bg-green-50 text-green-700 hover:bg-green-100 border-green-200"
        >
          Normale
        </Badge>
      );
    case TaskPriority.haute:
      return (
        <Badge
          variant="outline"
          className="bg-amber-50 text-amber-700 hover:bg-amber-100 border-amber-200"
        >
          Haute
        </Badge>
      );
    case TaskPriority.critique:
      return (
        <Badge
          variant="outline"
          className="bg-red-50 text-red-700 hover:bg-red-100 border-red-200"
        >
          Critique
        </Badge>
      );
    default:
      return <Badge variant="outline">{priority}</Badge>;
  }
};
