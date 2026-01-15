import { useMutation, useQueryClient } from "@tanstack/react-query";
import { logoutUser } from "../lib/api/services/auth.service";
import { queries } from "../lib/api/queries";

const useDeAuthMutation = () => {
  const queryClient = useQueryClient();
  const mutation = useMutation({
    mutationFn: () => logoutUser(),
    onSuccess: () => {
      // Invalidate the current user query.
      queryClient.invalidateQueries({
        queryKey: queries.users.getCurrentUser.queryKey,
      });
    },
  });
  return mutation;
};

export { useDeAuthMutation };
