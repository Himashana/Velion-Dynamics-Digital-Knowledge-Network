// src/lib/api/services/user.service.ts

import { apiClient } from "../../apiClient";
import { NewUser } from "../@types/new-user";
import { User } from "../@types/user";

// Function to get the current authenticated user's details
export const getCurrentUser = async () => {
  const response = await apiClient.get<User>(`/route/users/me`);
  return response.json();
};

// Function to register a new user
export const registerUser = async (userData: NewUser) => {
  const response = await apiClient.post<User>(`/route/users/register`, {
    body: JSON.stringify(userData),
  });
  return response.json();
};
