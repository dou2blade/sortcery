import { useMutation, useQueryClient } from "@tanstack/react-query";
import { apiPut } from "@/utils/api";
import { productKeys } from "./productKeys";
import { ProductFormData } from "../schemas";

export const useUpdateProduct = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: ({ id, payload }: { 
      id: number, 
      payload: ProductFormData 
    }) => apiPut(`products/${id}`, payload),

    onSuccess: (data, { id }) => {
      queryClient.invalidateQueries({
        queryKey: productKeys.lists(),
      });

      queryClient.invalidateQueries({
        queryKey: productKeys.detail(id),
      });

      return data;
    },
  });
};
