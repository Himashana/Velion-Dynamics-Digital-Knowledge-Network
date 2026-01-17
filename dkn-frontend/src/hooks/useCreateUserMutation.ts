import { useMutation } from "@tanstack/react-query";
import { NewUser } from "../lib/api/@types/new-user";
import { registerUser } from "../lib/api/services/user.service";

const useCreateUserMutation = () => {
  const mutation = useMutation({
    mutationFn: (userData: NewUser) => registerUser(userData),
  });
  return mutation;
};

export { useCreateUserMutation };
