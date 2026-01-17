import { createQueryKeys } from "@lukemorales/query-key-factory";
import {
  getAllWorkspaces,
  getWorkspaceById,
} from "../services/workspace.service";

export const workspaces = createQueryKeys("workspaces", {
  all: null,
  getAllWorkspaces: {
    queryKey: null,
    queryFn: () => getAllWorkspaces(),
  },
  getWorkspaceById: (workspaceId: number) => ({
    queryKey: [workspaceId],
    queryFn: () => getWorkspaceById(workspaceId),
  }),
});
