"use client";

import { queries } from "@/src/lib/api/queries";
import { useQuery } from "@tanstack/react-query";
import { useState } from "react";
import { UpdateContentSection } from "./UpdateContentSection";
import { Pencil } from "lucide-react";
import { CommentsSection } from "../Comment/CommentsSection";

export function ViewContentSection({ contentId }: { contentId: number }) {
  const [isUpdateContentOpen, setIsUpdateContentOpen] = useState(false);

  const { data: content, isLoading: isContentLoading } = useQuery({
    ...queries.contents.getContentById(contentId),
  });

  return (
    <div className="mt-6">
      {isContentLoading ? (
        <div>Loading content...</div>
      ) : content ? (
        <div className="p-4 border rounded-lg">
          <h2 className="text-2xl font-bold mb-4">
            {content.filePath.split("\\").pop() || "Untitled"}
          </h2>
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
