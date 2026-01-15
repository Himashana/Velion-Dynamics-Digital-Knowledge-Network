import { cookies } from "next/headers";
import { NextResponse } from "next/server";

export async function POST(req: Request) {
  const body = await req.json();
  const baseUrl = process.env.NEXT_PUBLIC_API_BASE_URL;

  // Forward the login request to the external API
  const res = await fetch(`${baseUrl}/auth/login`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(body),
  });

  // Get the response data
  const data = await res.json();

  // If login failed, return an error response
  if (!res.ok) {
    return NextResponse.json({ message: "Login failed" }, { status: 401 });
  }

  // Store token in HTTP-only cookie
  (await cookies()).set({
    name: "session",
    value: data.token,
    httpOnly: true,
    secure: process.env.NODE_ENV === "production",
    sameSite: "strict",
    maxAge: 60 * 60, // expires in 1 hour
    path: "/",
  });

  return NextResponse.json({ success: true });
}

// Logout
export async function DELETE() {
  // Clear the session cookie
  (await cookies()).delete("session");
  return NextResponse.json({ success: true });
}
