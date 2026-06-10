import { useMutation, useQueryClient } from "@tanstack/react-query";
import { apiDelete } from "@/utils/api";
import { storeKeys } from "./storeKeys";

export const useDeleteStore = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: (id: number) => apiDelete(`stores/${id}`),

    onSuccess: (data, id) => {
      queryClient.removeQueries({
        queryKey: storeKeys.detail(id),
      });

      queryClient.invalidateQueries({
        queryKey: storeKeys.stats(),
      });

      queryClient.invalidateQueries({
        queryKey: storeKeys.lists(),
      });

      return data;
    },
  });
};
