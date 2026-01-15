import { createQueryKeys } from "@lukemorales/query-key-factory";
import { getAllWorkspaces } from "../services/workspace.service";

export const workspaces = createQueryKeys("workspaces", {
  all: null,
  getAllWorkspaces: {
    queryKey: null,
    queryFn: () => getAllWorkspaces(),
  },
});
