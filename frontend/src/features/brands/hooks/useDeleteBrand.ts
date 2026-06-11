import { useMutation, useQueryClient } from "@tanstack/react-query";
import { apiDelete } from "@/utils/api";
import { brandKeys } from "./brandKeys";

export const useDeleteBrand = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: (id: number) => apiDelete(`brands/${id}`),

    onSuccess: (data, id) => {
      queryClient.removeQueries({
        queryKey: brandKeys.detail(id),
      });

      queryClient.invalidateQueries({
        queryKey: brandKeys.stats(),
      });

      queryClient.invalidateQueries({
        queryKey: brandKeys.lists(),
      });

      return data;
    },
  });
};
