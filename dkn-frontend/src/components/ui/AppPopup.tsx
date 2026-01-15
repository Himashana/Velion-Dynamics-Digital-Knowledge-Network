"use client";

export function AppPopup({ children }: { children: React.ReactNode }) {
  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center bg-black/40">
      <div className="bg-gray-800 rounded-lg shadow-lg p-6 m-4">{children}</div>
    </div>
  );
}
