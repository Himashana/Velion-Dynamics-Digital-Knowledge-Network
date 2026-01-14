"use client";

import { useAuthMutation } from "@/src/hooks/useAuthMutation";
import { useRouter } from "next/navigation";
import { useState } from "react";

export default function LoginPage() {
  const router = useRouter();
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const loginMutation = useAuthMutation();

  const onSubmit = () => {
    loginMutation.mutate(
      { email, password },
      {
        onSuccess: () => {
          // router.push("/dashboard");
        },
      }
    );
  };

  return (
    <form
      onSubmit={(e) => {
        e.preventDefault();
        onSubmit();
      }}
    >
      <input value={email} onChange={(e) => setEmail(e.target.value)} />
      <input
        type="password"
        value={password}
        onChange={(e) => setPassword(e.target.value)}
      />

      <button type="submit">
        {loginMutation.isPending ? "Logging in..." : "Login"}
      </button>
    </form>
  );
}
