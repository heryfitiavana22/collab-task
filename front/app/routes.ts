import { type RouteConfig, index, layout, route } from "@react-router/dev/routes";

export default [
    layout("routes/layout.tsx", [
        index("routes/home.tsx"),
        route("tasks/new", "routes/task-create.tsx"),
        route("tasks/:taskId", "routes/task-update.tsx")
    ])
] satisfies RouteConfig;
