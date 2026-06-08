import { useMutation, useQueryClient } from "@tanstack/react-query";
import { apiPut } from "@/utils/api";
import { userKeys } from "./userKeys";
import { UserFormData } from "../schemas";

export const useUpdateUser = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: ({ id, data }: { 
      id: number, 
      data: UserFormData 
    }) => apiPut(`users/${id}`, data),

    onSuccess: (data, { id }) => {
      queryClient.invalidateQueries({
        queryKey: userKeys.lists(),
      });

      queryClient.invalidateQueries({
        queryKey: userKeys.detail(id),
      });

      return data;
    },
  });
};
