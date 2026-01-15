"use client";

import { useAuthMutation } from "@/src/hooks/useAuthMutation";
import { useRouter } from "next/navigation";
import { useState } from "react";

export function LoginSection() {
  const router = useRouter();
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const loginMutation = useAuthMutation();

  const onSubmit = () => {
    loginMutation.mutate(
      { email, password },
      {
        onSuccess: () => {
          router.push("/");
        },
      }
    );
  };

  return (
    <div>
      <div className="flex min-h-screen items-center justify-center font-sans bg-black">
        <div className="w-full max-w-md rounded-lg p-8 shadow-md bg-gray-800">
          <h2 className="mb-6 text-2xl font-bold text-white">
            Velion Dynamics Login
          </h2>
          <form
            onSubmit={(e) => {
              e.preventDefault();
              onSubmit();
            }}
          >
            <div className="mb-4">
              <label
                htmlFor="email"
                className="mb-2 block text-sm font-medium text-gray-300"
              >
                Email
              </label>
              <input
                type="email"
                id="email"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                className="w-full rounded border px-3 py-2 focus:border-app-primary focus:outline-none bg-gray-700 border-gray-600 text-white"
                required
              />
            </div>
            <div className="mb-6">
              <label
                htmlFor="password"
                className="mb-2 block text-sm font-medium text-gray-300"
              >
                Password
              </label>
              <input
                type="password"
                id="password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                className="w-full rounded border px-3 py-2 focus:border-app-primary focus:outline-none bg-gray-700 border-gray-600 text-white"
                required
              />
            </div>
            <button
              type="submit"
              className="w-full rounded bg-app-primary px-4 py-2 font-bold text-white hover:bg-app-primary/90 focus:outline-none focus:ring-2 focus:ring-app-primary focus:ring-offset-2 cursor-pointer"
            >
              {loginMutation.isPending ? "Authenticating..." : "Sign In"}
            </button>
          </form>

          {loginMutation.isError && (
            <div className="bg-red-100 mt-4 rounded p-3 text-red-700">
              Failed to login. Please check your credentials and try again.
            </div>
          )}
          {loginMutation.isSuccess && (
            <div className="bg-green-100 mt-4 rounded p-3 text-green-700">
              Login successful! Redirecting...
            </div>
          )}
        </div>
      </div>
    </div>
  );
}
