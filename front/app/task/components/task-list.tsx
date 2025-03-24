import type React from "react";

import { useState, useEffect } from "react";
import { Card, CardContent, CardHeader } from "~/components/ui/card";
import { Button } from "~/components/ui/button";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "~/components/ui/select";
import { Input } from "~/components/ui/input";
import {
  Pagination,
  PaginationContent,
  PaginationItem,
  PaginationLink,
  PaginationNext,
  PaginationPrevious,
} from "~/components/ui/pagination";
import { TaskCard } from "./task-card";
import { Badge } from "~/components/ui/badge";
import { SearchIcon, FilterIcon } from "lucide-react";
import {
  Sheet,
  SheetContent,
  SheetHeader,
  SheetTitle,
  SheetTrigger,
} from "~/components/ui/sheet";
import { Label } from "~/components/ui/label";
import { Link, useSearchParams } from "react-router";
import { useNavigate } from "react-router";
import { TaskPriority, TaskStatus, type PaginatedTask } from "../core/task";
import { taskService } from "../core/service";

export function TaskList() {
  const [searchParams, setSearchParams] = useSearchParams();
  let navigate = useNavigate();
  const [tasks, setTasks] = useState<PaginatedTask>({
    data: [],
    page: { page: 1, size: 10 },
    totalPage: 0,
  });
  const [loading, setLoading] = useState(true);
  const [search, setSearch] = useState(searchParams.get("search") || "");
  const [status, setStatus] = useState(searchParams.get("status") || "");
  const [priority, setPriority] = useState(searchParams.get("priority") || "");
  const page = Number(searchParams.get("page") || 1);

  useEffect(() => {
    const fetchTasks = async () => {
      setLoading(true);

      const result = await taskService.findAll();
      if (result.isOk()) {
        setTasks(result.value);
      } else {
        console.log(result.asyncMap);
      }
      setLoading(false);
    };

    fetchTasks();
  }, [page, search, status, priority]);

  const handleSearch = (e: React.FormEvent) => {
    e.preventDefault();
    updateFilters({ search });
  };

  const updateFilters = (newFilters: Record<string, string>) => {
    const params = new URLSearchParams(searchParams);

    Object.entries(newFilters).forEach(([key, value]) => {
      if (value) {
        params.set(key, value);
      } else {
        params.delete(key);
      }
    });

    // Reset to page 1 when filters change
    if (Object.keys(newFilters).some((key) => key !== "page")) {
      params.set("page", "1");
    }

    navigate(`/tasks?${params.toString()}`);
  };

  const clearFilters = () => {
    setStatus("");
    setPriority("");
    navigate("/");
  };

  const activeFilters = [
    status && `Statut: ${status}`,
    priority && `Priorité: ${priority}`,
  ].filter(Boolean);

  return (
    <div className="space-y-6">
      <div className="flex flex-col sm:flex-row gap-4 justify-between">
        <form onSubmit={handleSearch} className="flex w-full sm:w-96 gap-2">
          <Input
            placeholder="Rechercher des tâches..."
            value={search}
            onChange={(e) => setSearch(e.target.value)}
            className="w-full"
          />
          <Button type="submit" variant="outline" size="icon">
            <SearchIcon className="h-4 w-4" />
          </Button>
        </form>

        <div className="flex items-center gap-2">
          <Sheet>
            <SheetTrigger asChild>
              <Button
                variant="outline"
                size="sm"
                className="flex items-center gap-2"
              >
                <FilterIcon className="h-4 w-4" />
                Filtres
                {activeFilters.length > 0 && (
                  <Badge variant="secondary" className="ml-1">
                    {activeFilters.length}
                  </Badge>
                )}
              </Button>
            </SheetTrigger>
            <SheetContent>
              <SheetHeader>
                <SheetTitle>Filtres</SheetTitle>
              </SheetHeader>
              <div className="p-4 space-y-4">
                <div className="space-y-2">
                  <Label htmlFor="status">Statut</Label>
                  <Select
                    value={status}
                    onValueChange={(value) => setStatus(value)}
                  >
                    <SelectTrigger id="status">
                      <SelectValue placeholder="Tous les statuts" />
                    </SelectTrigger>
                    <SelectContent>
                      <SelectItem value="all">Tous les statuts</SelectItem>
                      <SelectItem value={TaskStatus.TO_DO}>À faire</SelectItem>
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
                </div>

                <div className="space-y-2">
                  <Label htmlFor="priority">Priorité</Label>
                  <Select
                    value={priority}
                    onValueChange={(value) => setPriority(value)}
                  >
                    <SelectTrigger id="priority">
                      <SelectValue placeholder="Toutes les priorités" />
                    </SelectTrigger>
                    <SelectContent>
                      <SelectItem value="all">Toutes les priorités</SelectItem>
                      <SelectItem value={TaskPriority.basse}>Basse</SelectItem>
                      <SelectItem value={TaskPriority.normale}>
                        Normale
                      </SelectItem>
                      <SelectItem value={TaskPriority.haute}>Haute</SelectItem>
                      <SelectItem value={TaskPriority.critique}>
                        Critique
                      </SelectItem>
                    </SelectContent>
                  </Select>
                </div>

                <div className="flex justify-between pt-4">
                  <Button variant="outline" onClick={clearFilters}>
                    Réinitialiser
                  </Button>
                  <Button onClick={() => updateFilters({ status, priority })}>
                    Appliquer
                  </Button>
                </div>
              </div>
            </SheetContent>
          </Sheet>
        </div>
      </div>

      {activeFilters.length > 0 && (
        <div className="flex flex-wrap gap-2">
          {activeFilters.map((filter, index) => (
            <Badge key={index} variant="secondary" className="text-sm">
              {filter}
            </Badge>
          ))}
          <Button
            variant="ghost"
            size="sm"
            onClick={clearFilters}
            className="h-6 px-2 text-xs"
          >
            Effacer tous les filtres
          </Button>
        </div>
      )}

      {loading ? (
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
          {Array.from({ length: 6 }).map((_, i) => (
            <Card key={i} className="animate-pulse">
              <CardHeader className="h-24 bg-muted rounded-t-lg" />
              <CardContent className="h-32 bg-muted/50" />
            </Card>
          ))}
        </div>
      ) : tasks.data.length === 0 ? (
        <Card className="w-full">
          <CardContent className="flex flex-col items-center justify-center py-12">
            <p className="text-muted-foreground mb-4">Aucune tâche trouvée</p>
            <Button asChild>
              <Link to="/tasks/new">Créer une tâche</Link>
            </Button>
          </CardContent>
        </Card>
      ) : (
        <>
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
            {tasks.data.map((task) => (
              <TaskCard key={task.id} task={task} />
            ))}
          </div>

          {tasks.totalPage > 1 && (
            <Pagination className="mt-8">
              <PaginationContent>
                <PaginationItem>
                  <PaginationPrevious
                    href={`/tasks?${new URLSearchParams({
                      ...Object.fromEntries(searchParams.entries()),
                      page: String(Math.max(1, page - 1)),
                    })}`}
                    aria-disabled={page <= 1}
                    className={
                      page <= 1 ? "pointer-events-none opacity-50" : ""
                    }
                  />
                </PaginationItem>

                {Array.from(
                  { length: Math.min(5, tasks.totalPage) },
                  (_, i) => {
                    const pageNumber = i + 1;
                    const isCurrentPage = pageNumber === page;

                    return (
                      <PaginationItem key={i}>
                        <PaginationLink
                          href={`/tasks?${new URLSearchParams({
                            ...Object.fromEntries(searchParams.entries()),
                            page: String(pageNumber),
                          })}`}
                          isActive={isCurrentPage}
                        >
                          {pageNumber}
                        </PaginationLink>
                      </PaginationItem>
                    );
                  }
                )}

                {tasks.totalPage > 5 && (
                  <>
                    <PaginationItem>
                      <span className="px-2">...</span>
                    </PaginationItem>
                    <PaginationItem>
                      <PaginationLink
                        href={`/tasks?${new URLSearchParams({
                          ...Object.fromEntries(searchParams.entries()),
                          page: String(tasks.totalPage),
                        })}`}
                        isActive={tasks.totalPage === page}
                      >
                        {tasks.totalPage}
                      </PaginationLink>
                    </PaginationItem>
                  </>
                )}

                <PaginationItem>
                  <PaginationNext
                    href={`/tasks?${new URLSearchParams({
                      ...Object.fromEntries(searchParams.entries()),
                      page: String(Math.min(tasks.totalPage, page + 1)),
                    })}`}
                    aria-disabled={page >= tasks.totalPage}
                    className={
                      page >= tasks.totalPage
                        ? "pointer-events-none opacity-50"
                        : ""
                    }
                  />
                </PaginationItem>
              </PaginationContent>
            </Pagination>
          )}
        </>
      )}
    </div>
  );
}
