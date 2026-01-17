import { useMutation, useQueryClient } from "@tanstack/react-query";
import { queries } from "../lib/api/queries";
import { createContentInWorkspace } from "../lib/api/services/content.service";

const useCreateWorkspaceContentMutation = () => {
  const queryClient = useQueryClient();
  const mutation = useMutation({
    mutationFn: ({ workspaceId, file }: { workspaceId: number; file: File }) =>
      createContentInWorkspace(workspaceId, file),
    onSuccess: (_, { workspaceId }) => {
      // Invalidate the contents query to refetch the updated list
      queryClient.invalidateQueries({
        queryKey:
          queries.contents.getContentsByWorkspaceId(workspaceId).queryKey,
      });
    },
  });
  return mutation;
};

export { useCreateWorkspaceContentMutation };
