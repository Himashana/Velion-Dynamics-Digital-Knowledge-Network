"use client";

import { queries } from "@/src/lib/api/queries";
import { useQuery } from "@tanstack/react-query";
import { Folder, MonitorCloud, X } from "lucide-react";
import Link from "next/link";
import { CreateWorkspaceSection } from "./CreateWorkspaceSection";
import { useState } from "react";
import { useDeleteWorkspaceMutation } from "@/src/hooks/useDeleteWorkspaceMutation";
import { UsersManagement } from "../Users/UsersManagement";

export function WorkspacesSection() {
  const [showCreateWorkspacePopup, setShowCreateWorkspacePopup] =
    useState(false);

  const { data: workspacesList, isLoading: isWorkspacesListLoading } = useQuery(
    {
      ...queries.workspaces.getAllWorkspaces,
    },
  );

  const deleteWorkspaceMutation = useDeleteWorkspaceMutation();

  const handleDeleteWorkspace = (workspaceId: number) => {
    // Delete with a confirmation
    if (confirm("Are you sure you want to delete this workspace?")) {
      deleteWorkspaceMutation.mutate(
        { workspaceId },
        {
          onError: () => {
            alert("Failed to delete workspace. Please try again.");
          },
        },
      );
    }
  };

  return (
    <div>
      <div className="min-h-100">
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
            <div className="flex flex-col md:flex-row flex-wrap gap-4">
              {workspacesList?.map((workspace) => (
                <Link
                  href={`/workspaces/${workspace.workspaceId}`}
                  key={workspace.workspaceId}
                  className="flex items-center space-x-4 p-4 border rounded-lg justify-between md:justify-start"
                >
                  <div className="flex items-center space-x-2 justify-center">
                    <Folder className="w-6 h-6 text-gray-500" />
                    <span className="text-lg font-medium">
                      {workspace.name}
                    </span>
                  </div>
                  <div className="flex items-center">
                    <button
                      className="text-red-500 hover:text-red-700 cursor-pointer"
                      onClick={(e) => {
                        // Prevent the link navigation when clicking delete
                        e.preventDefault();
                        e.stopPropagation();
                        // Call the delete handler
                        handleDeleteWorkspace(workspace.workspaceId);
                      }}
                    >
                      <X className="w-4 h-4" />
                    </button>
                  </div>
                </Link>
              ))}
            </div>
          )}
        </div>

        {showCreateWorkspacePopup && (
          <CreateWorkspaceSection
            onClose={() => setShowCreateWorkspacePopup(false)}
          />
        )}
      </div>

      {/* Users management section */}
      <UsersManagement />
    </div>
  );
}
