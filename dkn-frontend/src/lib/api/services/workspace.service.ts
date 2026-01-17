// src/lib/api/services/workspace.service.ts

import { apiClient } from "../../apiClient";
import { Workspace } from "../@types/workspace";

// Function to create a new workspace
export const createWorkspace = async (name: string, description?: string) => {
  const response = await apiClient.post<Workspace>(
    `/route/digital-workspaces`,
    {
      body: JSON.stringify({
        name,
        description,
      }),
    },
  );
  return response.json();
};

// Function to get all workspaces
export const getAllWorkspaces = async () => {
  const response = await apiClient.get<Workspace[]>(
    `/route/digital-workspaces`,
  );
  return response.json();
};

// Function to get a workspace by its ID
export const getWorkspaceById = async (workspaceId: number) => {
  const response = await apiClient.get<Workspace>(
    `/route/digital-workspaces/${workspaceId}`,
  );
  return response.json();
};

// Function to delete a workspace by its ID
export const deleteWorkspace = async (workspaceId: number) => {
  const response = await apiClient.delete<void>(
    `/route/digital-workspaces/${workspaceId}`,
  );
  return response.json();
};
