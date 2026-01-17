"use client";

import { queries } from "@/src/lib/api/queries";
import { useQuery } from "@tanstack/react-query";
import { User } from "lucide-react";
import Link from "next/link";

export function UsersManagement() {
  const { data: currentUser } = useQuery({
    ...queries.users.getCurrentUser,
  });

  if (currentUser && currentUser?.permissionLevel === 1) {
    return (
      <div className="mt-10">
        <div>
          <h3 className="flex items-center space-x-2 text-xl font-normal mt-2 mb-2">
            <User /> <p>Users Management</p>
          </h3>

          <div className="mt-6 pt-4 pt-8 border-t border-gray-600">
            {/* Register new user button */}
            <Link
              href="/users/new"
              className="px-4 py-2 bg-app-primary text-white rounded hover:bg-app-primary/90 cursor-pointer"
            >
              Register New User
            </Link>
          </div>
        </div>
      </div>
    );
  } else {
    return null;
  }
}
