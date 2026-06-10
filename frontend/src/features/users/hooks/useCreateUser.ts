import { useMutation, useQueryClient } from "@tanstack/react-query";
import { apiPost } from "@/utils/api";
import { userKeys } from "./userKeys";
import { UserFormData } from "../schemas";

export const useCreateUser = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: (payload: UserFormData) => apiPost("users", payload),
    onSuccess: (data) => {
      queryClient.invalidateQueries({
        queryKey: userKeys.all,
      });

      return data;
    },
  });
};
