import { useMutation, useQueryClient } from "@tanstack/react-query";
import { authenticateUser } from "../lib/api/services/auth.service";
import { queries } from "../lib/api/queries";

const useAuthMutation = () => {
  const queryClient = useQueryClient();
  const mutation = useMutation({
    mutationFn: ({ email, password }: { email: string; password: string }) =>
      authenticateUser(email, password),
    onSuccess: () => {
      // Invalidate the current user query to refetch user data after authentication
      queryClient.invalidateQueries({
        queryKey: queries.users.getCurrentUser.queryKey,
      });
    },
  });
  return mutation;
};

export { useAuthMutation };
