import { createQueryKeys } from "@lukemorales/query-key-factory";
import { getAllComments } from "../services/comment.service";

export const comments = createQueryKeys("comments", {
  all: null,
  getAllCommentsByContentId: (contentId: number) => ({
    queryKey: [contentId],
    queryFn: () => getAllComments(contentId),
  }),
});
