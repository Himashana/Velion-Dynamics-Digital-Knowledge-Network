"use client";

import { queries } from "@/src/lib/api/queries";
import { useQuery } from "@tanstack/react-query";
import { useState } from "react";
import { UpdateContentSection } from "./UpdateContentSection";
import { Flag, Pencil } from "lucide-react";
import { CommentsSection } from "../Comment/CommentsSection";
import { useFlagContentMutation } from "@/src/hooks/useFlagContentMutation";

export function ViewContentSection({ contentId }: { contentId: number }) {
  const [isUpdateContentOpen, setIsUpdateContentOpen] = useState(false);

  const { data: currentUser } = useQuery({
    ...queries.users.getCurrentUser,
  });

  const { data: content, isLoading: isContentLoading } = useQuery({
    ...queries.contents.getContentById(contentId),
  });

  const flagContentMutation = useFlagContentMutation();

  const flagContent = () => {
    flagContentMutation.mutate({ contentId });
  };

  return (
    <div className="mt-6">
      {isContentLoading ? (
        <div>Loading content...</div>
      ) : content ? (
        <div className="p-4 border rounded-lg">
          <div className="flex flex-col md:flex-row space-x-2 md:items-center mb-8">
            <h2 className="text-2xl font-bold">
              {content.filePath.split("\\").pop() || "Untitled"}
            </h2>
            {/* Only the Leadership and Knowledge Governance Council can report content */}
            {!content.flagged &&
            currentUser &&
            (currentUser.permissionLevel === 1 ||
              currentUser.permissionLevel === 3) ? (
              <div>
                <button
                  className="cursor-pointer flex items-center space-x-2 text-red-500 hover:text-red-700"
                  onClick={flagContent}
                >
                  <Flag className="ml-4 h-5 w-5 text-red-500 hover:text-red-700" />{" "}
                  <p>Report content</p>
                </button>
              </div>
            ) : (
              // Display only if content is already flagged for the normal consultants
              content.flagged && (
                <p className="text-red-500 font-semibold">
                  This content has been reported.
                </p>
              )
            )}
          </div>
          <iframe
            src={`/route/files-route/contents/${contentId}/preview`}
            title="Content Preview"
            className="w-full h-140 bg-white"
          ></iframe>
          <div className="mt-4 flex">
            <div className="text-white">
              {content.tags && content.tags.length > 0 ? (
                <p>
                  {content.tags.map((tag, index) => (
                    <span key={index} className="mr-2">
                      #{tag}
                    </span>
                  ))}
                </p>
              ) : (
                <p className="text-gray-400">No tags available.</p>
              )}
            </div>
            <button
              className="cursor-pointer"
              onClick={() => setIsUpdateContentOpen(true)}
            >
              <Pencil className="ml-4 h-5 w-5 text-white/80 hover:text-white" />
            </button>
          </div>
        </div>
      ) : (
        <div>Content not found.</div>
      )}

      <CommentsSection contentId={contentId} />

      {isUpdateContentOpen && content && (
        <UpdateContentSection
          content={content}
          onClose={() => setIsUpdateContentOpen(false)}
        />
      )}
    </div>
  );
}
