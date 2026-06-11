import { useMutation, useQueryClient } from "@tanstack/react-query";
import { apiPost } from "@/utils/api";
import { productCategoryKeys } from "./productCategoryKeys";
import { ProductCategoryFormData } from "../schemas";

export const useCreateProductCategory = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: (payload: ProductCategoryFormData) => apiPost("product-categories", payload),
    onSuccess: (data) => {
      queryClient.invalidateQueries({
        queryKey: productCategoryKeys.all,
      });

      return data;
    },
  });
};
