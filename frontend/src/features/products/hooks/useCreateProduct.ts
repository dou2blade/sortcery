import { useMutation, useQueryClient } from "@tanstack/react-query";
import { apiPost } from "@/utils/api";
import { productKeys } from "./productKeys";
import { ProductFormData } from "../schemas";

export const useCreateProduct = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: (data: ProductFormData) => apiPost("products", data),
    onSuccess: (data) => {
      queryClient.invalidateQueries({
        queryKey: productKeys.all,
      });

      return data;
    },
  });
};
