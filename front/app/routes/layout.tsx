import { Outlet } from "react-router";

export default function Layout() {
  return (
    <div className="mx-auto container py-6 space-y-6">
      <Outlet />
    </div>
  );
}
