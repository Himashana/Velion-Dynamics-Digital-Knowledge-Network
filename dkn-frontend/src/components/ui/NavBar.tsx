"use client";

import { queries } from "@/src/lib/api/queries";
import { useQuery } from "@tanstack/react-query";

export function NavBar() {
  const { data: currentUser } = useQuery({
    ...queries.users.getCurrentUser,
  });

  console.log("Current User:", currentUser);

  return (
    <nav className="w-full bg-black/90 p-4 backdrop-blur-md shadow-md flex flex-col md:flex-row md:justify-between items-center">
      <div>
        <h1 className="text-white text-2xl font-bold">Velion Dynamics DKN</h1>
      </div>
      <div className="flex items-center mt-4 md:mt-0 flex-row gap-2">
        <p className="text-white mr-4">Welcome, User</p>
        <button className="text-white bg-red-600 px-4 py-0 md:py-2 rounded hover:bg-red-700 transition cursor-pointer">
          Logout
        </button>
      </div>
    </nav>
  );
}
