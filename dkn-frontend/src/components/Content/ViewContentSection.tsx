"use client";

import { queries } from "@/src/lib/api/queries";
import { useQuery } from "@tanstack/react-query";

export function ViewContentSection({ contentId }: { contentId: number }) {
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
        </div>
      ) : (
        <div>Content not found.</div>
      )}
    </div>
  );
}
