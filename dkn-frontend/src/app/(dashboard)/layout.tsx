import { cookies } from "next/headers";
import { redirect } from "next/navigation";

export default async function DashboardLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  const token = (await cookies()).get("session");

  if (!token) {
    redirect("/login");
  }

  return <main>{children}</main>;
}
