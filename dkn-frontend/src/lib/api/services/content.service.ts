// src/lib/api/services/content.service.ts

import { apiClient } from "../../apiClient";
import { Content } from "../@types/content";

// Function to create a new content
export const createContent = async (file: File) => {
  const response = await apiClient.post<Content>(
    `/route/files-route/contents/upload`,
    {
      body: JSON.stringify({
        file,
      }),
    },
  );
  return response.json();
};

// Function to create a new content in a specified workspace
export const createContentInWorkspace = async (
  workspaceId: number,
  file: File,
) => {
  const formData = new FormData();
  formData.append("file", file);

  const response = await apiClient.post<Content>(
    `/route/files-route/contents/upload/${workspaceId}`,
    {
      body: formData,
    },
  );
  return response.json();
};

// Function to get all contents
export const getAllContents = async () => {
  const response = await apiClient.get<Content[]>(`/route/contents`);
  return response.json();
};

// Function to get contents by workspace ID
export const getContentsByWorkspaceId = async (workspaceId: number) => {
  const response = await apiClient.get<Content[]>(
    `/route/contents/workspace/${workspaceId}`,
  );
  return response.json();
};

// Function to get a content by its ID
export const getContentById = async (contentId: number) => {
  const response = await apiClient.get<Content>(`/route/contents/${contentId}`);
  return response.json();
};

// Function to delete a content by its ID
export const deleteContent = async (contentId: number) => {
  const response = await apiClient.delete<void>(`/route/contents/${contentId}`);
  return response.json();
};
