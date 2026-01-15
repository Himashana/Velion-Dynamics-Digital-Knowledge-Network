import { redirect } from "next/navigation";

export default function DashboardPage() {
  // Redirect to the workspaces page
  redirect("/workspaces");
}
