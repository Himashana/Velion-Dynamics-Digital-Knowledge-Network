import { useMutation, useQueryClient } from "@tanstack/react-query";
import { authenticateUser } from "../lib/api/services/auth.service";

const useAuthMutation = () => {
  const queryClient = useQueryClient();
  const mutation = useMutation({
    mutationFn: ({ email, password }: { email: string; password: string }) =>
      authenticateUser(email, password),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["currentUser"] });
    },
  });
  return mutation;
};

export { useAuthMutation };
