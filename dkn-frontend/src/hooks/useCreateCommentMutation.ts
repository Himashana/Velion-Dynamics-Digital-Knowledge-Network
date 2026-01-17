import { useMutation, useQueryClient } from "@tanstack/react-query";
import { queries } from "../lib/api/queries";
import { createComment } from "../lib/api/services/comment.service";

const useCreateCommentMutation = () => {
  const queryClient = useQueryClient();
  const mutation = useMutation({
    mutationFn: ({
      comment,
      contentId,
    }: {
      comment: string;
      contentId: number;
    }) => createComment(comment, contentId),
    onSuccess: (_, { contentId }) => {
      // Invalidate the comments query to refetch the updated list
      queryClient.invalidateQueries({
        queryKey:
          queries.comments.getAllCommentsByContentId(contentId).queryKey,
      });
    },
  });
  return mutation;
};

export { useCreateCommentMutation };
