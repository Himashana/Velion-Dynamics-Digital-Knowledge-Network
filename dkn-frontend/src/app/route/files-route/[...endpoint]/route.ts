// src/app/route/files-route/[...endpoint]/route.ts
// This is a common route for handling all files handling route-related requests

import { cookies } from "next/headers";
import { NextRequest, NextResponse } from "next/server";

// Handle GET requests to dynamic endpoints
export async function GET(
  request: NextRequest,
  context: { params: Promise<{ endpoint: string[] }> },
) {
  const baseUrl = process.env.NEXT_PUBLIC_API_BASE_URL;
  const token = (await cookies()).get("session")?.value;

  // Extract the dynamic endpoint from the route parameters
  const params = await context.params;
  const path = params.endpoint.join("/");

  const url = new URL(`${baseUrl}/${path}`);
  // Append query parameters from the original request to the new URL
  request.nextUrl.searchParams.forEach((v, k) => url.searchParams.append(k, v));

  try {
    // Forward the GET request to the appropriate backend service with authentication header
    const res = await fetch(url.toString(), {
      method: "GET",
      headers: {
        ...(token && { Authorization: `Bearer ${token}` }),
      },
    });
    return new NextResponse(res.body, { status: res.status });
  } catch (e: any) {
    return NextResponse.json({ error: e.message }, { status: 502 });
  }
}

// Handle POST requests to dynamic endpoints
export async function POST(
  request: NextRequest,
  context: { params: Promise<{ endpoint: string[] }> },
) {
  const baseUrl = process.env.NEXT_PUBLIC_API_BASE_URL;
  const token = (await cookies()).get("session")?.value;

  // Extract the dynamic endpoint from the route parameters
  const params = await context.params;
  const path = params.endpoint.join("/");
  const url = new URL(`${baseUrl}/${path}`);

  try {
    // Forward the POST request to the appropriate backend service with authentication header
    const res = await fetch(url.toString(), {
      method: "POST",
      headers: {
        ...(token && { Authorization: `Bearer ${token}` }),
        "Content-Type":
          request.headers.get("Content-Type") || "application/json",
      },
      body: await request.text(),
    });
    return NextResponse.json(await res.json(), {
      status: res.status,
    });
  } catch (e: any) {
    return NextResponse.json({ error: e.message }, { status: 502 });
  }
}
