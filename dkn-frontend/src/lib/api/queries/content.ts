import { createQueryKeys } from "@lukemorales/query-key-factory";
import {
  getAllContents,
  getContentById,
  getContentsByWorkspaceId,
} from "../services/content.service";

export const contents = createQueryKeys("contents", {
  all: null,
  getAllContents: {
    queryKey: null,
    queryFn: () => getAllContents(),
  },
  getContentsByWorkspaceId: (workspaceId: number) => ({
    queryKey: ["all-contents", workspaceId],
    queryFn: () => getContentsByWorkspaceId(workspaceId),
  }),
  getContentById: (contentId: number) => ({
    queryKey: [contentId],
    queryFn: () => getContentById(contentId),
  }),
});
