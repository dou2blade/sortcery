import { useMutation, useQueryClient } from "@tanstack/react-query";
import { apiPost } from "@/utils/api";
import { userKeys } from "./userKeys";
import { UserFormData } from "../schemas";

export const useCreateUser = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: (data: UserFormData) => apiPost("users", data),
    onSuccess: (data) => {
      queryClient.invalidateQueries({
        queryKey: userKeys.all,
      });

      return data;
    },
  });
};
