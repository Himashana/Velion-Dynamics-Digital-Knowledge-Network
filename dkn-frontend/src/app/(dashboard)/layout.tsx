import { NavBar } from "@/src/components/ui/NavBar";
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

  return (
    <main className="min-h-screen bg-gray-800">
      <NavBar />
      <div className="h-[calc(100vh-72px)] font-sans bg-gray-800 text-white p-4 overflow-auto">
        {children}
      </div>
    </main>
  );
}
