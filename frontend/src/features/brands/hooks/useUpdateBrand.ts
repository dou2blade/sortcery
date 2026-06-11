import { useMutation, useQueryClient } from "@tanstack/react-query";
import { apiPut } from "@/utils/api";
import { brandKeys } from "./brandKeys";
import { BrandFormData } from "../schemas";

export const useUpdateBrand = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: ({ id, payload }: { 
      id: number, 
      payload: BrandFormData 
    }) => apiPut(`brands/${id}`, payload),

    onSuccess: (data, { id }) => {
      queryClient.invalidateQueries({
        queryKey: brandKeys.lists(),
      });

      queryClient.invalidateQueries({
        queryKey: brandKeys.detail(id),
      });

      return data;
    },
  });
};
