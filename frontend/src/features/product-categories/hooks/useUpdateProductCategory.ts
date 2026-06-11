import { useMutation, useQueryClient } from "@tanstack/react-query";
import { apiPut } from "@/utils/api";
import { productCategoryKeys } from "./productCategoryKeys";
import { ProductCategoryFormData } from "../schemas";

export const useUpdateProductCategory = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: ({ id, payload }: { 
      id: number, 
      payload: ProductCategoryFormData 
    }) => apiPut(`product-categories/${id}`, payload),

    onSuccess: (data, { id }) => {
      queryClient.invalidateQueries({
        queryKey: productCategoryKeys.lists(),
      });

      queryClient.invalidateQueries({
        queryKey: productCategoryKeys.detail(id),
      });

      return data;
    },
  });
};
