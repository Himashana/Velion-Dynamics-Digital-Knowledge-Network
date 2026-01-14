import { cookies } from "next/headers";
import ky, { HTTPError } from "ky";

// Ensure that the API base URL is defined
if (!process.env.NEXT_PUBLIC_API_BASE_URL) {
  throw new Error("NEXT_PUBLIC_API_BASE_URL is required");
}

export interface ExtendedHTTPError extends HTTPError {
  _data?: any;
}

// Use ky to create a pre-configured API client
// Usage: only for the authenticated requests after login.
export const apiClient = ky.create({
  prefixUrl: process.env.NEXT_PUBLIC_API_BASE_URL,
  hooks: {
    beforeRequest: [
      async (request) => {
        // Skip adding the token if NO-TOKEN header is present
        if (request.headers.get("NO-TOKEN")) return;

        // Retrieve the token from cookies (server-side)
        const token = (await cookies()).get("session")?.value;
        // Add the Authorization header if the token exists
        if (token) request.headers.set("Authorization", `Bearer ${token}`);
      },
    ],
    beforeError: [
      // Enhance HTTPError with response data
      async (error: ExtendedHTTPError) => {
        const clonedResponse = error.response.clone();
        const errorData = await clonedResponse.json();
        error._data = errorData;

        return error;
      },
    ],
  },
});
