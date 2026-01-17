"use client";

import { useDeleteContentMutation } from "@/src/hooks/useDeleteContentMutation";
import { queries } from "@/src/lib/api/queries";
import { useQuery } from "@tanstack/react-query";
import { File, X } from "lucide-react";
import Link from "next/link";

export function ViewContentsSection({ workspaceId }: { workspaceId?: number }) {
  const { data: contentsList, isLoading: isContentsLoading } = useQuery({
    ...queries.contents.getContentsByWorkspaceId(workspaceId!),
    enabled: workspaceId !== undefined,
  });

  const deleteContentMutation = useDeleteContentMutation();

  const handleDeleteContent = (contentId: number) => {
    // Delete with a confirmation
    if (confirm("Are you sure you want to delete this content?")) {
      deleteContentMutation.mutate(
        { contentId, workspaceId },
        {
          onError: () => {
            alert("Failed to delete content. Please try again.");
          },
        },
      );
    }
  };

  return (
    <div className="mt-6">
      {isContentsLoading ? (
        <div>Loading contents...</div>
      ) : (
        <div className="flex flex-col flex-wrap gap-4">
          {contentsList?.map((content) => (
            <Link
              href={`/contents/${content.contentId}`}
              key={content.contentId}
              className="flex items-center space-x-4 p-4 border rounded-lg justify-between"
            >
              <div className="flex items-center space-x-2 justify-center">
                <File className="w-6 h-6 text-gray-500" />
                <span className="text-lg font-medium">
                  {
                    // Extract the file name from the file path
                    content.filePath.split("\\").pop() || "Untitled"
                  }
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
                    handleDeleteContent(content.contentId);
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
