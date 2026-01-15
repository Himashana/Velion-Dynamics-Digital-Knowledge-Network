"use client";

import { queries } from "@/src/lib/api/queries";
import { useQuery } from "@tanstack/react-query";
import { Folder, MonitorCloud } from "lucide-react";
import Link from "next/link";
import { CreateWorkspaceSection } from "./CreateWorkspaceSection";
import { useState } from "react";

export function WorkspacesSection() {
  const [showCreateWorkspacePopup, setShowCreateWorkspacePopup] =
    useState(false);

  const { data: workspacesList, isLoading: isWorkspacesListLoading } = useQuery(
    {
      ...queries.workspaces.getAllWorkspaces,
    }
  );

  return (
    <div>
      <div className="flex items-center justify-between">
        <div>
          <h3 className="flex items-center space-x-2 text-xl font-normal mt-2 mb-2">
            <MonitorCloud /> <p>My Workspaces</p>
          </h3>
        </div>
        <div>
          <button
            className="px-4 py-2 bg-app-primary text-white rounded hover:bg-app-primary/90 cursor-pointer"
            onClick={() => setShowCreateWorkspacePopup(true)}
          >
            Create Workspace
          </button>
        </div>
      </div>
      <div className="mt-6">
        {isWorkspacesListLoading ? (
          <div>Loading workspaces...</div>
        ) : (
          <div className="flex flex-row flex-wrap gap-4">
            {workspacesList?.map((workspace) => (
              <Link
                href={`/workspaces/${workspace.workspaceId}`}
                key={workspace.workspaceId}
                className="flex items-center space-x-2 p-4 border rounded-lg"
              >
                <Folder className="w-6 h-6 text-gray-500" />
                <span className="text-lg font-medium">{workspace.name}</span>
              </Link>
            ))}
          </div>
        )}
      </div>

      {showCreateWorkspacePopup && <CreateWorkspaceSection />}
    </div>
  );
}
