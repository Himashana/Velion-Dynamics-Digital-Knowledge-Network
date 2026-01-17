import { useMutation, useQueryClient } from "@tanstack/react-query";
import { queries } from "../lib/api/queries";
import { updateContent } from "../lib/api/services/content.service";

const useUpdateContentMutation = () => {
  const queryClient = useQueryClient();
  const mutation = useMutation({
    mutationFn: ({ contentId, tags }: { contentId: number; tags: string[] }) =>
      updateContent(contentId, tags),
    onSuccess: (_, { contentId }) => {
      // Invalidate the content query to refetch the updated data
      queryClient.invalidateQueries({
        queryKey: queries.contents.getContentById(contentId).queryKey,
      });
    },
  });
  return mutation;
};

export { useUpdateContentMutation };
