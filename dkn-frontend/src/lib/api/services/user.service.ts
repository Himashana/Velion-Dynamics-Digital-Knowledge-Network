// src/lib/api/services/user.service.ts

import { User } from "../@types/user";

// Function to get the current authenticated user's details
export const getCurrentUser = async () => {
  return await fetch(`/route/users/me`, {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
    },
  })
    .then(async (response) => {
      if (!response.ok) {
        throw new Error("Failed to fetch current user");
      }
      return response.json();
    })
    .then((data: User) => data);
};
