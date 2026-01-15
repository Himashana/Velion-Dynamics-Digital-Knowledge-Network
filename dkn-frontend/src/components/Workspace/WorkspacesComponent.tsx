"use client";

import { queries } from "@/src/lib/api/queries";
import { useQuery } from "@tanstack/react-query";

export function WorkspacesComponent() {
  const { data: workspacesList, isLoading: isWorkspacesListLoading } = useQuery(
    {
      ...queries.workspaces.getAllWorkspaces,
    }
  );

  return <div>Welcome to the Workspaces!!!</div>;
}
