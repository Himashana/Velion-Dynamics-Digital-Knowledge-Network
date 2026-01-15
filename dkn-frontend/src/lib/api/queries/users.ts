import { createQueryKeys } from "@lukemorales/query-key-factory";
import { getCurrentUser } from "../services/user.service";

export const users = createQueryKeys("users", {
  all: null,
  getCurrentUser: {
    queryKey: ["currentUser"],
    queryFn: () => getCurrentUser(),
  },
});
