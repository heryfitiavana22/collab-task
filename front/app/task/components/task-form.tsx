import { useState, useEffect } from "react";
import { format } from "date-fns";
import { fr } from "date-fns/locale";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "~/components/ui/form";
import { Input } from "~/components/ui/input";
import { Button } from "~/components/ui/button";
import { Textarea } from "~/components/ui/textarea";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "~/components/ui/select";
import {
  Popover,
  PopoverContent,
  PopoverTrigger,
} from "~/components/ui/popover";
import { Calendar } from "~/components/ui/calendar";
import {
  Command,
  CommandEmpty,
  CommandGroup,
  CommandInput,
  CommandItem,
  CommandList,
} from "~/components/ui/command";
import { Badge } from "~/components/ui/badge";
import { Card, CardContent } from "~/components/ui/card";
import { CalendarIcon, X } from "lucide-react";
import { cn } from "~/lib/utils";
import {
  CreateTaskSchema,
  TaskPriority,
  TaskStatus,
  UpdateTaskSchema,
  type CreateTask,
  type Task,
  type UpdateTask,
} from "../core/task";
import type { User } from "~/user/core/user";
import { userService } from "~/user/core/service";
import { toast } from "sonner";
import { useNavigate } from "react-router";
import { taskService } from "../core/service";

type FormValues = CreateTask | UpdateTask;

interface TaskFormProps {
  task?: Task;
}

export default function TaskForm({ task }: TaskFormProps) {
  const [users, setUsers] = useState<User[]>([]);
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [selectedUsers, setSelectedUsers] = useState<User[]>([]);
  const navigate = useNavigate();

  const form = useForm<FormValues>({
    resolver: zodResolver(task ? UpdateTaskSchema : CreateTaskSchema),
    defaultValues: {
      title: task?.title || "",
      description: task?.description || "",
      status: task?.status || TaskStatus.TO_DO,
      priority: task?.priority || TaskPriority.normale,
      dueDate: task?.dueDate ? new Date(task.dueDate) : new Date(),
      assignedUserIds: task?.assignedUsers?.map((user) => user.id) || [],
      createdByUserId: task?.createdBy?.id || "",
    },
  });

  useEffect(() => {
    const fetchUsers = async () => {
      const result = await userService.findAll();
      if (result.isOk()) {
        setUsers(result.value.data);
      } else {
        toast("Erreur", {
          description: "Impossible de récupérer la liste des utilisateurs.",
        });
      }
    };

    fetchUsers();
  }, [task, toast]);

  function isCreate(values: FormValues): values is CreateTask {
    return !task && "assignedUserIds" in values && "createdByUserId" in values;
  }

  const onSubmit = async (values: FormValues) => {
    setIsSubmitting(true);
    if (isCreate(values)) {
      const result = await taskService.create(values);
      if (result.isOk()) {
        toast("Tâche créée", {
          description: "La tâche a été créée avec succès.",
        });
      } else {
        toast("Erreur", {
          description:
            "Une erreur est survenue lors de l'enregistrement de la tâche.",
        });
      }
    } else {
      if (!task) return;
      const result = await taskService.update(task.id, values);
      if (result.isOk()) {
        toast("Tâche mise à jour", {
          description: "La tâche a été mise à jour avec succès.",
        });
      } else {
        toast("Erreur", {
          description:
            "Une erreur est survenue lors de l'enregistrement de la tâche.",
        });
      }
    }
    navigate("/");
    setIsSubmitting(false);
  };

  const handleUserSelect = (userId: string) => {
    const user = users.find((u) => u.id === userId);
    if (!user) return;

    // Add user to selected users if not already selected
    if (!selectedUsers.some((u) => u.id === userId)) {
      setSelectedUsers([...selectedUsers, user]);

      // Update form value
      const currentIds = form.getValues("assignedUserIds");
      form.setValue("assignedUserIds", [...currentIds, userId], {
        shouldValidate: true,
      });
    }
  };

  const handleUserRemove = (userId: string) => {
    // Remove user from selected users
    setSelectedUsers(selectedUsers.filter((u) => u.id !== userId));

    // Update form value
    const currentIds = form.getValues("assignedUserIds");
    form.setValue(
      "assignedUserIds",
      currentIds.filter((id) => id !== userId),
      { shouldValidate: true }
    );
  };

  return (
    <Card>
      <CardContent className="pt-6">
        <Form {...form}>
          <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-6">
            <FormField
              control={form.control}
              name="title"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Titre</FormLabel>
                  <FormControl>
                    <Input placeholder="Titre de la tâche" {...field} />
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />

            <FormField
              control={form.control}
              name="description"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Description</FormLabel>
                  <FormControl>
                    <Textarea
                      placeholder="Description détaillée de la tâche"
                      className="min-h-[120px]"
                      {...field}
                    />
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />

            <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
              <FormField
                control={form.control}
                name="status"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Statut</FormLabel>
                    <Select
                      onValueChange={field.onChange}
                      defaultValue={field.value}
                    >
                      <FormControl>
                        <SelectTrigger>
                          <SelectValue placeholder="Sélectionner un statut" />
                        </SelectTrigger>
                      </FormControl>
                      <SelectContent>
                        <SelectItem value={TaskStatus.TO_DO}>
                          À faire
                        </SelectItem>
                        <SelectItem value={TaskStatus.IN_PROGRESS}>
                          En cours
                        </SelectItem>
                        <SelectItem value={TaskStatus.BLOCKED}>
                          Bloquée
                        </SelectItem>
                        <SelectItem value={TaskStatus.COMPLETED}>
                          Terminée
                        </SelectItem>
                        <SelectItem value={TaskStatus.OVERDUE}>
                          En retard
                        </SelectItem>
                      </SelectContent>
                    </Select>
                    <FormMessage />
                  </FormItem>
                )}
              />

              <FormField
                control={form.control}
                name="priority"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Priorité</FormLabel>
                    <Select
                      onValueChange={field.onChange}
                      defaultValue={field.value}
                    >
                      <FormControl>
                        <SelectTrigger>
                          <SelectValue placeholder="Sélectionner une priorité" />
                        </SelectTrigger>
                      </FormControl>
                      <SelectContent>
                        <SelectItem value={TaskPriority.basse}>
                          Basse
                        </SelectItem>
                        <SelectItem value={TaskPriority.normale}>
                          Normale
                        </SelectItem>
                        <SelectItem value={TaskPriority.haute}>
                          Haute
                        </SelectItem>
                        <SelectItem value={TaskPriority.critique}>
                          Critique
                        </SelectItem>
                      </SelectContent>
                    </Select>
                    <FormMessage />
                  </FormItem>
                )}
              />
            </div>

            <FormField
              control={form.control}
              name="dueDate"
              render={({ field }) => (
                <FormItem className="flex flex-col">
                  <FormLabel>Date d'échéance</FormLabel>
                  <Popover>
                    <PopoverTrigger asChild>
                      <FormControl>
                        <Button
                          variant={"outline"}
                          className={cn(
                            "w-full pl-3 text-left font-normal",
                            !field.value && "text-muted-foreground"
                          )}
                        >
                          {field.value ? (
                            format(field.value, "d MMMM yyyy", { locale: fr })
                          ) : (
                            <span>Sélectionner une date</span>
                          )}
                          <CalendarIcon className="ml-auto h-4 w-4 opacity-50" />
                        </Button>
                      </FormControl>
                    </PopoverTrigger>
                    <PopoverContent className="w-auto p-0" align="start">
                      <Calendar
                        mode="single"
                        selected={field.value}
                        onSelect={field.onChange}
                        initialFocus
                        locale={fr}
                      />
                    </PopoverContent>
                  </Popover>
                  <FormMessage />
                </FormItem>
              )}
            />

            <FormField
              control={form.control}
              name="assignedUserIds"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Utilisateurs assignés</FormLabel>
                  <div className="space-y-4">
                    <Popover>
                      <PopoverTrigger asChild>
                        <FormControl>
                          <Button
                            variant="outline"
                            role="combobox"
                            className={cn(
                              "w-full justify-between",
                              !field.value.length && "text-muted-foreground"
                            )}
                          >
                            Sélectionner des utilisateurs
                            <span className="ml-2">
                              {field.value.length > 0 && (
                                <Badge variant="secondary">
                                  {field.value.length} sélectionné(s)
                                </Badge>
                              )}
                            </span>
                          </Button>
                        </FormControl>
                      </PopoverTrigger>
                      <PopoverContent className="w-full p-0">
                        <Command>
                          <CommandInput placeholder="Rechercher un utilisateur..." />
                          <CommandList>
                            <CommandEmpty>
                              Aucun utilisateur trouvé.
                            </CommandEmpty>
                            <CommandGroup>
                              {users.map((user) => (
                                <CommandItem
                                  key={user.id}
                                  value={user.id}
                                  onSelect={() => handleUserSelect(user.id)}
                                >
                                  <div className="flex items-center">
                                    <span>{user.username}</span>
                                    <span className="ml-2 text-xs text-muted-foreground">
                                      {user.email}
                                    </span>
                                  </div>
                                </CommandItem>
                              ))}
                            </CommandGroup>
                          </CommandList>
                        </Command>
                      </PopoverContent>
                    </Popover>

                    {selectedUsers.length > 0 && (
                      <div className="flex flex-wrap gap-2 mt-2">
                        {selectedUsers.map((user) => (
                          <Badge
                            key={user.id}
                            variant="secondary"
                            className="flex items-center gap-1"
                          >
                            {user.username}
                            <Button
                              variant="ghost"
                              size="icon"
                              className="h-4 w-4 p-0 ml-1"
                              onClick={() => handleUserRemove(user.id)}
                            >
                              <X className="h-3 w-3" />
                            </Button>
                          </Badge>
                        ))}
                      </div>
                    )}
                  </div>
                  <FormMessage />
                </FormItem>
              )}
            />
            <FormField
              control={form.control}
              name="createdByUserId"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Créateur</FormLabel>
                  <Select
                    onValueChange={field.onChange}
                    defaultValue={field.value}
                  >
                    <FormControl>
                      <SelectTrigger>
                        <SelectValue placeholder="Sélectionner un créateur" />
                      </SelectTrigger>
                    </FormControl>
                    <SelectContent>
                      {users.map((user) => (
                        <SelectItem key={user.id} value={user.id}>
                          {user.username} ({user.email})
                        </SelectItem>
                      ))}
                    </SelectContent>
                  </Select>
                  <FormMessage />
                </FormItem>
              )}
            />

            <div className="flex justify-end space-x-4 pt-4">
              <Button
                type="button"
                variant="outline"
                onClick={() => navigate("/")}
                disabled={isSubmitting}
              >
                Annuler
              </Button>
              <Button type="submit" disabled={isSubmitting}>
                {isSubmitting
                  ? "Enregistrement..."
                  : task
                  ? "Mettre à jour"
                  : "Créer"}
              </Button>
            </div>
          </form>
        </Form>
      </CardContent>
    </Card>
  );
}
