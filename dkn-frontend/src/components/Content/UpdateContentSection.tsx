import { useState } from "react";
import { AppPopup } from "../ui/AppPopup";
import { Content } from "@/src/lib/api/@types/content";
import { useUpdateContentMutation } from "@/src/hooks/useUpdateContentMutation";
import { useQueryClient } from "@tanstack/react-query";
import { queries } from "@/src/lib/api/queries";

export interface UpdateContentDetails {
  tags: string[];
}

export function UpdateContentSection({
  content,
  onClose,
}: {
  content: Content;
  onClose?: () => void;
}) {
  const queryClient = useQueryClient();

  const [updatedContentDetails, setUpdatedContentDetails] =
    useState<UpdateContentDetails>({
      tags: content.tags || [],
    });

  const [tagsInput, setTagsInput] = useState(
    updatedContentDetails.tags.join(", "),
  );

  const updateContentMutation = useUpdateContentMutation();

  const onSubmit = () => {
    updateContentMutation.mutate(
      {
        contentId: content.contentId,
        tags: updatedContentDetails.tags,
      },
      {
        onSuccess: () => {
          queryClient.invalidateQueries({
            queryKey: queries.contents.getContentById(content.contentId)
              .queryKey,
          });

          onClose?.();
        },
      },
    );
  };

  return (
    <AppPopup>
      <div>
        <form
          className="space-y-4"
          onSubmit={(e) => {
            e.preventDefault();
            onSubmit();
          }}
        >
          <div>
            <label
              htmlFor="workspaceName"
              className="block text-sm font-medium mb-1"
            >
              <textarea
                id="tags"
                className="mt-1 block w-full border border-gray-300 rounded-md shadow-sm p-2"
                rows={3}
                placeholder="Enter tags separated by commas..."
                value={tagsInput}
                onChange={(e) => {
                  setTagsInput(e.target.value);

                  // Split input by commas and trim whitespace
                  setUpdatedContentDetails({
                    ...updatedContentDetails,
                    tags: e.target.value
                      .split(",")
                      .map((tag) => tag.trim())
                      .filter((tag) => tag.length > 0),
                  });
                }}
              ></textarea>
            </label>
          </div>

          {updateContentMutation.isError && (
            <div className="text-red-500">
              Error updating content data. Please try again.
            </div>
          )}
          <div className="flex justify-end space-x-2">
            <button
              type="button"
              className="px-4 py-2 bg-gray-500 text-white rounded hover:bg-gray-600 cursor-pointer"
              onClick={() => onClose?.()}
            >
              Cancel
            </button>
            <button
              type="submit"
              className="px-4 py-2 bg-app-primary text-white rounded hover:bg-app-primary/90 cursor-pointer"
              disabled={updateContentMutation.isPending}
            >
              Save
            </button>
          </div>
        </form>
      </div>
    </AppPopup>
  );
}
