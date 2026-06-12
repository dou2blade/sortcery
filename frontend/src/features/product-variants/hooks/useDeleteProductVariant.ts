import { useMutation, useQueryClient } from "@tanstack/react-query";
import { apiDelete } from "@/utils/api";
import { productVariantKeys } from "./productVariantKeys";

export const useDeleteProductVariant = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: (id: number) => apiDelete(`product-variants/${id}`),

    onSuccess: (data, id) => {
      queryClient.removeQueries({
        queryKey: productVariantKeys.detail(id),
      });

      queryClient.invalidateQueries({
        queryKey: productVariantKeys.lists(),
      });

      return data;
    },
  });
};
