import { useMutation, useQueryClient } from "@tanstack/react-query";
import { queries } from "../lib/api/queries";
import { deleteContent } from "../lib/api/services/content.service";

const useDeleteContentMutation = () => {
  const queryClient = useQueryClient();
  const mutation = useMutation({
    mutationFn: ({ contentId }: { contentId: number; workspaceId?: number }) =>
      deleteContent(contentId),
    onSuccess: (_, { contentId, workspaceId }) => {
      // Invalidate the contents query to refetch the updated list
      if (workspaceId) {
        queryClient.invalidateQueries({
          queryKey:
            queries.contents.getContentsByWorkspaceId(workspaceId).queryKey,
        });
      }

      queryClient.invalidateQueries({
        queryKey: queries.contents.getAllContents.queryKey,
      });
    },
  });
  return mutation;
};

export { useDeleteContentMutation };
