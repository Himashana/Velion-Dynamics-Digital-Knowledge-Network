// src/lib/api/services/workspace.service.ts

import { apiClient } from "../../apiClient";
import { Workspace } from "../@types/workspace";

// Function to get the current authenticated user's details
export const getAllWorkspaces = async () => {
  const response = await apiClient.get<Workspace[]>(
    `/route/digital-workspaces`
  );
  return response.json();
};
