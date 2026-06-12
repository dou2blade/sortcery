import { useMutation, useQueryClient } from "@tanstack/react-query";
import { apiPost } from "@/utils/api";
import { productVariantKeys } from "./productVariantKeys";
import { ProductVariantFormData } from "../schemas";

export const useCreateProductVariant = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: (payload: ProductVariantFormData) => apiPost("product-variants", payload),
    onSuccess: (data) => {
      queryClient.invalidateQueries({
        queryKey: productVariantKeys.all,
      });

      return data;
    },
  });
};
