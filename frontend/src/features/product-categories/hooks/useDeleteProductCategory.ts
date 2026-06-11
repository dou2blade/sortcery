import { useMutation, useQueryClient } from "@tanstack/react-query";
import { apiDelete } from "@/utils/api";
import { productCategoryKeys } from "./productCategoryKeys";

export const useDeleteProductCategory = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: (id: number) => apiDelete(`product-categories/${id}`),

    onSuccess: (data, id) => {
      queryClient.removeQueries({
        queryKey: productCategoryKeys.detail(id),
      });

      queryClient.invalidateQueries({
        queryKey: productCategoryKeys.lists(),
      });

      return data;
    },
  });
};
