import { useCreateCommentMutation } from "@/src/hooks/useCreateCommentMutation";
import { queries } from "@/src/lib/api/queries";
import { useQuery } from "@tanstack/react-query";

export function CommentsSection({ contentId }: { contentId: number }) {
  const createCommentMutation = useCreateCommentMutation();

  const { data: commentsList, isLoading: isCommentsLoading } = useQuery({
    ...queries.comments.getAllCommentsByContentId(contentId),
  });

  const onSubmit = (commentText: string) => {
    createCommentMutation.mutate({ comment: commentText, contentId });
  };

  return (
    <div className="mt-6">
      {/* Comment form */}
      <form
        className="mb-4"
        onSubmit={(e) => {
          e.preventDefault();
          const formData = new FormData(e.currentTarget);
          const commentText = formData.get("comment") as string;
          onSubmit(commentText);
          e.currentTarget.reset();
        }}
      >
        <textarea
          name="comment"
          className="w-full rounded p-2 bg-gray-700"
          placeholder="Write a comment..."
          required
        ></textarea>
        <button
          type="submit"
          className="mt-2 px-4 py-2 bg-app-primary text-white rounded cursor-pointer"
        >
          Submit Comment
        </button>
      </form>

      {/* Comments list */}
      <div className="mt-6">
        {isCommentsLoading ? (
          <div>Loading comments...</div>
        ) : (
          <div className="space-y-4">
            {commentsList?.map((comment) => (
              <div key={comment.id} className="p-4 rounded-lg bg-gray-700">
                <div className="flex flex-col md:flex-row justify-between mb-2">
                  <div>
                    <p className="font-bold text-white mb-2">
                      {comment.commentedBy.name}
                    </p>
                  </div>
                  <div>
                    <p className="text-gray-400 text-sm italic">
                      {new Date(comment.timestamp).toLocaleString()}
                    </p>
                  </div>
                </div>
                <p className="text-gray-300">{comment.comment}</p>
              </div>
            ))}
          </div>
        )}
      </div>
    </div>
  );
}
