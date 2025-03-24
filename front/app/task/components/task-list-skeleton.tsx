import { Card, CardContent, CardHeader } from "~/components/ui/card";

export function TaskListSkeleton() {
  return (
    <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
      {Array.from({ length: 6 }).map((_, i) => (
        <Card key={i} className="animate-pulse">
          <CardHeader className="h-24 bg-muted rounded-t-lg" />
          <CardContent className="h-32 bg-muted/50" />
        </Card>
      ))}
    </div>
  );
}
