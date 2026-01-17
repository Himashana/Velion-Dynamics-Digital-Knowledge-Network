import { useState } from "react";
import { AppPopup } from "../ui/AppPopup";
import { useCreateWorkspaceContentMutation } from "@/src/hooks/useCreateWorkspaceContentMutation";

export interface CreateContentDetails {
  workspaceId: number;
  file: File | null;
}

export function CreateContentSection({
  workspaceId,
  onClose,
}: {
  workspaceId: number;
  onClose?: () => void;
}) {
  const [newContentDetails, setNewContentDetails] =
    useState<CreateContentDetails>({
      workspaceId,
      file: null,
    });

  const createWorkspaceContentMutation = useCreateWorkspaceContentMutation();

  const onSubmit = () => {
    if (!newContentDetails.file) return;

    createWorkspaceContentMutation.mutate(
      {
        workspaceId: newContentDetails.workspaceId,
        file: newContentDetails.file,
      },
      {
        onSuccess: () => {
          onClose?.();
        },
      },
    );
  };

  return (
    <AppPopup>
      <div>
        <h3 className="text-xl font-normal mb-4">Create New Content</h3>
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
              Choose File
            </label>
            <input
              type="file"
              id="fileInput"
              className="w-full border border-gray-300 rounded px-3 py-2"
              onChange={(e) => {
                const file = e.target.files ? e.target.files[0] : null;
                setNewContentDetails((prev) => ({
                  ...prev,
                  file,
                }));
              }}
            />
          </div>

          {createWorkspaceContentMutation.isError && (
            <div className="text-red-500">
              Error creating workspace content. Please try again.
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
              disabled={createWorkspaceContentMutation.isPending}
            >
              Create
            </button>
          </div>
        </form>
      </div>
    </AppPopup>
  );
}
