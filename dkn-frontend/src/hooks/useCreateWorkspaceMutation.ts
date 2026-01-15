import { useMutation, useQueryClient } from "@tanstack/react-query";
import { queries } from "../lib/api/queries";
import { createWorkspace } from "../lib/api/services/workspace.service";

const useCreateWorkspaceMutation = () => {
  const queryClient = useQueryClient();
  const mutation = useMutation({
    mutationFn: ({
      name,
      description,
    }: {
      name: string;
      description?: string;
    }) => createWorkspace(name, description),
    onSuccess: () => {
      // Invalidate the workspaces query to refetch the updated list
      queryClient.invalidateQueries({
        queryKey: queries.workspaces.getAllWorkspaces.queryKey,
      });
    },
  });
  return mutation;
};

export { useCreateWorkspaceMutation };
