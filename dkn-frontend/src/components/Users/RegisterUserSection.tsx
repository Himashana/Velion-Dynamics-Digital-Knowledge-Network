"use client";

import { useCreateUserMutation } from "@/src/hooks/useCreateUserMutation";
import { NewUser } from "@/src/lib/api/@types/new-user";
import { useState } from "react";

export function RegisterUserSection() {
  const createUserMutation = useCreateUserMutation();

  const [userData, setUserData] = useState<NewUser>({
    name: "",
    email: "",
    password: "",
    contactNumber: "",
    address: "",
    permissionLevel: 6, // Junior Consultant by default
    performanceMetrics: {},
  });

  const [expertDomainsInput, setExpertDomainsInput] = useState<string>("");

  const onSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    createUserMutation.mutate(userData, {
      onSuccess: () => {
        // Clear form
        setUserData({
          name: "",
          email: "",
          password: "",
          contactNumber: "",
          address: "",
          permissionLevel: 6,
          performanceMetrics: {},
        });
        setExpertDomainsInput("");
      },
    });
  };

  return (
    <div>
      <h3 className="text-xl font-normal mt-2 mb-2">Register New User</h3>

      <div className="w-full md:max-w-100">
        <form onSubmit={onSubmit} className="space-y-4">
          <div>
            <label className="block mb-1">
              Name <span className="text-red-500">*</span>
            </label>
            <input
              type="text"
              value={userData.name}
              onChange={(e) =>
                setUserData({ ...userData, name: e.target.value })
              }
              className="w-full bg-gray-700 px-3 py-2"
              required
            />
          </div>
          <div>
            <label className="block mb-1">
              Email <span className="text-red-500">*</span>
            </label>
            <input
              type="email"
              value={userData.email}
              onChange={(e) =>
                setUserData({ ...userData, email: e.target.value })
              }
              className="w-full bg-gray-700 px-3 py-2"
              required
            />
          </div>
          <div>
            <label className="block mb-1">
              Password <span className="text-red-500">*</span>
            </label>
            <input
              type="password"
              value={userData.password}
              onChange={(e) =>
                setUserData({ ...userData, password: e.target.value })
              }
              className="w-full bg-gray-700 px-3 py-2"
              required
            />
          </div>
          <div>
            <label className="block mb-1">
              Contact Number <span className="text-red-500">*</span>
            </label>
            <input
              type="text"
              value={userData.contactNumber}
              onChange={(e) =>
                setUserData({ ...userData, contactNumber: e.target.value })
              }
              className="w-full bg-gray-700 px-3 py-2"
              required
            />
          </div>
          <div>
            <label className="block mb-1">
              Address <span className="text-red-500">*</span>
            </label>
            <input
              type="text"
              value={userData.address}
              onChange={(e) =>
                setUserData({ ...userData, address: e.target.value })
              }
              className="w-full bg-gray-700 px-3 py-2"
              required
            />
          </div>
          <div>
            <label className="block mb-1">
              Permission Level <span className="text-red-500">*</span>
            </label>
            <select
              value={userData.permissionLevel}
              onChange={(e) =>
                setUserData({
                  ...userData,
                  permissionLevel: Number(e.target.value),
                })
              }
              className="w-full bg-gray-700 px-3 py-2"
              required
            >
              <option value={1}>1 - Leadership</option>
              <option value={2}>2 - Knowladge Champion</option>
              <option value={3}>3 - Knowladge Governance Council</option>
              <option value={4}>4 -</option>
              <option value={5}>5 - Senior Consultant</option>
              <option value={6}>6 - Junior Consultant</option>
            </select>
          </div>
          <div>
            <label className="block mb-1">Designation</label>
            <input
              type="text"
              value={userData.designation || ""}
              onChange={(e) =>
                setUserData({ ...userData, designation: e.target.value })
              }
              className="w-full bg-gray-700 px-3 py-2"
            />
          </div>
          <div>
            <label className="block mb-1">Responsibility</label>
            <input
              type="text"
              value={userData.responsibility || ""}
              onChange={(e) =>
                setUserData({ ...userData, responsibility: e.target.value })
              }
              className="w-full bg-gray-700 px-3 py-2"
            />
          </div>
          <div>
            <label className="block mb-1">
              Office Location <span className="text-red-500">*</span>
            </label>
            <input
              type="text"
              value={userData.officeLocation || ""}
              onChange={(e) =>
                setUserData({ ...userData, officeLocation: e.target.value })
              }
              className="w-full bg-gray-700 px-3 py-2"
              required
            />
          </div>
          <div>
            <label className="block mb-1">
              Expert Domains (comma separated)
            </label>
            <input
              type="text"
              value={expertDomainsInput}
              onChange={(e) => {
                setExpertDomainsInput(e.target.value);
                const domains = e.target.value
                  .split(",")
                  .map((domain) => domain.trim())
                  .filter((domain) => domain.length > 0);
                setUserData({
                  ...userData,
                  expertDomains: domains.length > 0 ? domains : null,
                });
              }}
              className="w-full bg-gray-700 px-3 py-2"
            />
          </div>
          <div>
            <button
              type="submit"
              className="bg-app-primary text-white px-4 py-2 rounded cursor-pointer disabled:opacity-50"
              disabled={createUserMutation.isPending}
            >
              {createUserMutation.isPending
                ? "Registering..."
                : "Register User"}
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}
