// src/lib/api/services/user.service.ts

import { apiClient } from "../../apiClient";
import { User } from "../@types/user";

// Function to get the current authenticated user's details
export const getCurrentUser = async () => {
  const response = await apiClient.get<User>(`/route/users/me`);
  return response.json();
};
