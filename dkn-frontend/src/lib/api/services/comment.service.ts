// src/lib/api/services/comment.service.ts

import { apiClient } from "../../apiClient";
import { Comment } from "../@types/comment";

// Function to create a new comment
export const createComment = async (comment: string, contentId: number) => {
  const response = await apiClient.post<Comment>(
    `/route/contents/comments/${contentId}/add`,
    {
      body: JSON.stringify({
        comment,
      }),
    },
  );
  return response.json();
};

// Function to get all comments
export const getAllComments = async (contentId: number) => {
  const response = await apiClient.get<Comment[]>(
    `/route/contents/comments/content/${contentId}`,
  );
  return response.json();
};

// Function to get a comment by its ID
export const getCommentById = async (commentId: number) => {
  const response = await apiClient.get<Comment>(
    `/route/contents/comments/${commentId}`,
  );
  return response.json();
};
