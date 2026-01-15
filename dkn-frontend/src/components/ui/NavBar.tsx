"use client";

import { useDeAuthMutation } from "@/src/hooks/useDeAuthMutation";
import { queries } from "@/src/lib/api/queries";
import { useQuery } from "@tanstack/react-query";
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
      <div className="flex items-center mt-4 md:mt-0 flex-row gap-2">
        {currentUser && (
          <p className="text-white mr-4">
            Welcome, {currentUser?.name.split(" ")[0]}
          </p>
        )}
        <button
          className="text-white bg-red-600 px-4 py-0 md:py-2 rounded hover:bg-red-700 transition cursor-pointer"
          onClick={handleLogout}
        >
          Logout
        </button>
      </div>
    </nav>
  );
}
