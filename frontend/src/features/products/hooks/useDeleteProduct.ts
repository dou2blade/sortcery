import { useMutation, useQueryClient } from "@tanstack/react-query";
import { apiDelete } from "@/utils/api";
import { productKeys } from "./productKeys";

export const useDeleteProduct = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: (id: number) => apiDelete(`products/${id}`),

    onSuccess: (data, id) => {
      queryClient.removeQueries({
        queryKey: productKeys.detail(id),
      });

      queryClient.invalidateQueries({
        queryKey: productKeys.stats(),
      });

      queryClient.invalidateQueries({
        queryKey: productKeys.lists(),
      });

      return data;
    },
  });
};
