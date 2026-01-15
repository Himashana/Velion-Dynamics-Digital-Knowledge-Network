import { useState } from "react";
import { AppPopup } from "../ui/AppPopup";
import { useCreateWorkspaceMutation } from "@/src/hooks/useCreateWorkspaceMutation";

export interface CreateWorkspaceDetails {
  name: string;
  description?: string;
}

export function CreateWorkspaceSection({ onClose }: { onClose?: () => void }) {
  const [newWorkspaceDetails, setNewWorkspaceDetails] =
    useState<CreateWorkspaceDetails>({
      name: "",
      description: "",
    });

  const createWorkspaceMutation = useCreateWorkspaceMutation();

  const onSubmit = () => {
    createWorkspaceMutation.mutate(newWorkspaceDetails, {
      onSuccess: () => {
        onClose?.();
      },
    });
  };

  return (
    <AppPopup>
      <div>
        <h3 className="text-xl font-normal mb-4">Create New Workspace</h3>
        <form
          className="space-y-4"
          onSubmit={(e) => {
            e.preventDefault();
            onSubmit();
          }}
        >
          <div>
            <label
              htmlFor="workspaceName"
              className="block text-sm font-medium mb-1"
            >
              Workspace Name
            </label>
            <input
              type="text"
              id="workspaceName"
              className="w-full px-3 py-2 border rounded"
              placeholder="Enter workspace name"
              value={newWorkspaceDetails.name}
              onChange={(e) =>
                setNewWorkspaceDetails({
                  ...newWorkspaceDetails,
                  name: e.target.value,
                })
              }
              required
            />
          </div>
          <div>
            <label
              htmlFor="workspaceDescription"
              className="block text-sm font-medium mb-1"
            >
              Description (optional)
            </label>
            <textarea
              id="workspaceDescription"
              className="w-full px-3 py-2 border rounded"
              placeholder="Enter description..."
              value={newWorkspaceDetails.description}
              onChange={(e) =>
                setNewWorkspaceDetails({
                  ...newWorkspaceDetails,
                  description: e.target.value,
                })
              }
            />
          </div>
          {createWorkspaceMutation.isError && (
            <div className="text-red-500">
              Error creating workspace. Please try again.
            </div>
          )}
          <div className="flex justify-end space-x-2">
            <button
              type="button"
              className="px-4 py-2 bg-gray-500 text-white rounded hover:bg-gray-600 cursor-pointer"
              onClick={() => onClose?.()}
            >
              Cancel
            </button>
            <button
              type="submit"
              className="px-4 py-2 bg-app-primary text-white rounded hover:bg-app-primary/90 cursor-pointer"
            >
              Create
            </button>
          </div>
        </form>
      </div>
    </AppPopup>
  );
}
