import { useMutation, useQueryClient } from "@tanstack/react-query";
import { apiPut } from "@/utils/api";
import { productVariantKeys } from "./productVariantKeys";
import { ProductVariantFormData } from "../schemas";

export const useUpdateProductVariant = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: ({ id, payload }: { 
      id: number, 
      payload: ProductVariantFormData 
    }) => apiPut(`product-variants/${id}`, payload),

    onSuccess: (data, { id }) => {
      queryClient.invalidateQueries({
        queryKey: productVariantKeys.lists(),
      });

      queryClient.invalidateQueries({
        queryKey: productVariantKeys.detail(id),
      });

      return data;
    },
  });
};
