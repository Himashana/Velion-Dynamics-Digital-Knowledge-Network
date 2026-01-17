"use client";

import { queries } from "@/src/lib/api/queries";
import { useQuery } from "@tanstack/react-query";
import { Folder, Link, X } from "lucide-react";

export function ViewContentsSection({ workspaceId }: { workspaceId?: number }) {
  const { data: contentsList, isLoading: isContentsLoading } = useQuery({
    ...queries.contents.getContentsByWorkspaceId(workspaceId!),
    enabled: workspaceId !== undefined,
  });

  return (
    <div className="mt-6">
      {isContentsLoading ? (
        <div>Loading contents...</div>
      ) : (
        <div className="flex flex-col md:flex-row flex-wrap gap-4">
          {contentsList?.map((content) => (
            <Link
              href={`/workspaces/${content.contentId}`}
              key={content.contentId}
              className="flex items-center space-x-4 p-4 border rounded-lg justify-between md:justify-start"
            >
              <div className="flex items-center space-x-2 justify-center">
                <Folder className="w-6 h-6 text-gray-500" />
                <span className="text-lg font-medium">{content.filePath}</span>
              </div>
              <div className="flex items-center">
                <button
                  className="text-red-500 hover:text-red-700 cursor-pointer"
                  onClick={(e) => {
                    // Prevent the link navigation when clicking delete
                    e.preventDefault();
                    e.stopPropagation();
                    // Call the delete handler
                    // handleDeleteWorkspace(workspace.workspaceId);
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
  );
}
