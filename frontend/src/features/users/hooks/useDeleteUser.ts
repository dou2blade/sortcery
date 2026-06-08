import { useMutation, useQueryClient } from "@tanstack/react-query";
import { apiDelete } from "@/utils/api";
import { userKeys } from "./userKeys";

export const useDeleteUser = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: (id: number) => apiDelete(`users/${id}`),

    onSuccess: (data, id) => {
      queryClient.removeQueries({
        queryKey: userKeys.detail(id),
      });

      queryClient.invalidateQueries({
        queryKey: userKeys.stats(),
      });

      queryClient.invalidateQueries({
        queryKey: userKeys.lists(),
      });

      return data;
    },
  });
};
