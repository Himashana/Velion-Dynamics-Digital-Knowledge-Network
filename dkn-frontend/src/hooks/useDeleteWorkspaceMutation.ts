import { useMutation, useQueryClient } from "@tanstack/react-query";
import { queries } from "../lib/api/queries";
import { deleteWorkspace } from "../lib/api/services/workspace.service";

const useDeleteWorkspaceMutation = () => {
  const queryClient = useQueryClient();
  const mutation = useMutation({
    mutationFn: ({ workspaceId }: { workspaceId: number }) =>
      deleteWorkspace(workspaceId),
    onSuccess: () => {
      // Invalidate the workspaces query to refetch the updated list
      queryClient.invalidateQueries({
        queryKey: queries.workspaces.getAllWorkspaces.queryKey,
      });
    },
  });
  return mutation;
};

export { useDeleteWorkspaceMutation };
