import { useMutation, useQueryClient } from "@tanstack/react-query";
import { queries } from "../lib/api/queries";
import { flagContent } from "../lib/api/services/content.service";

const useFlagContentMutation = () => {
  const queryClient = useQueryClient();
  const mutation = useMutation({
    mutationFn: ({ contentId }: { contentId: number }) =>
      flagContent(contentId),
    onSuccess: (_, { contentId }) => {
      // Invalidate the content query to refetch the updated data
      queryClient.invalidateQueries({
        queryKey: queries.contents.getContentById(contentId).queryKey,
      });
    },
  });
  return mutation;
};

export { useFlagContentMutation };
