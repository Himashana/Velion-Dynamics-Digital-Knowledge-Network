// src/lib/api/services/auth.service.ts

// Function to authenticate user by sending email and password to the backend
export const authenticateUser = async (email: string, password: string) => {
  // Make a POST request to the authentication endpoint
  const response = await fetch("/auth/login", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ email, password }),
  });

  // Throw an error if the response is not ok
  if (!response.ok) throw new Error("Login failed");

  // Return the parsed JSON response
  return response.json();
};

// Function to logout user by clearing the session cookie
export const logoutUser = async () => {
  // Make a DELETE request to the logout endpoint
  const response = await fetch("/auth/login", {
    method: "DELETE",
  });

  // Throw an error if the response is not ok
  if (!response.ok) throw new Error("Logout failed");

  return response.json();
};
