"use client";

import { queries } from "@/src/lib/api/queries";
import { useQuery } from "@tanstack/react-query";
import { Folder } from "lucide-react";
import { ViewContentsSection } from "../Content/ViewContentsSection";
import { useState } from "react";
import { CreateContentSection } from "../Content/CreateContentSection";

export function ViewWorkspaceSection({ workspaceId }: { workspaceId: number }) {
  const [showCreateContentPopup, setShowCreateContentPopup] = useState(false);

  const { data: workspaceData, isLoading: isWorkspaceLoading } = useQuery({
    ...queries.workspaces.getWorkspaceById(workspaceId),
  });

  const { data: contentsList, isLoading: isContentsLoading } = useQuery({
    ...queries.contents.getContentsByWorkspaceId(workspaceId),
  });

  return (
    <div>
      <div className="flex items-center justify-between">
        <div>
          <h3 className="flex items-center space-x-2 text-xl font-normal mt-2 mb-2">
            <Folder /> <p>{workspaceData?.name}</p>
          </h3>
        </div>
        <div>
          <button
            className="px-4 py-2 bg-app-primary text-white rounded hover:bg-app-primary/90 cursor-pointer"
            onClick={() => setShowCreateContentPopup(true)}
          >
            New File
          </button>
        </div>
      </div>

      <ViewContentsSection workspaceId={workspaceId} />

      {showCreateContentPopup && (
        <CreateContentSection
          workspaceId={workspaceId}
          onClose={() => setShowCreateContentPopup(false)}
        />
      )}
    </div>
  );
}
