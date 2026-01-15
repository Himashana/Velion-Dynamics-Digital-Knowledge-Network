"use client";

import { useDeAuthMutation } from "@/src/hooks/useDeAuthMutation";
import { queries } from "@/src/lib/api/queries";
import { useQuery } from "@tanstack/react-query";
import { LogOut } from "lucide-react";
import Link from "next/link";
import { useRouter } from "next/navigation";

export function NavBar() {
  const router = useRouter();

  const { data: currentUser } = useQuery({
    ...queries.users.getCurrentUser,
  });

  const deAuthMutation = useDeAuthMutation();

  const handleLogout = () => {
    deAuthMutation.mutate();
    router.replace("/login");
  };

  return (
    <nav className="w-full bg-black p-4 backdrop-blur-md shadow-md flex flex-col md:flex-row md:justify-between items-center">
      <Link href="/">
        <h1 className="text-white text-2xl font-bold">Velion Dynamics DKN</h1>
      </Link>
      <div className="flex items-center mt-4 md:mt-0 flex-row">
        {currentUser && (
          <p className="text-white mr-4">
            Welcome, {currentUser?.name.split(" ")[0]}
          </p>
        )}
        <button
          className="text-red-500 rounded hover:text-red-600 transition cursor-pointer"
          onClick={handleLogout}
        >
          <LogOut className="w-5 h-5" />
        </button>
      </div>
    </nav>
  );
}
