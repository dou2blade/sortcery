import { useMutation, useQueryClient } from "@tanstack/react-query";
import { apiPut } from "@/utils/api";
import { branchProductVariantKeys } from "./branchProductVariantKeys";
import { BranchProductVariantFormData } from "../schemas";

export const useUpdateBranchProductVariant = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: ({ id, payload }: { 
      id: number, 
      payload: BranchProductVariantFormData 
    }) => apiPut(`branch-product-variants/${id}`, payload),

    onSuccess: (data, { id }) => {
      queryClient.invalidateQueries({
        queryKey: branchProductVariantKeys.lists(),
      });

      queryClient.invalidateQueries({
        queryKey: branchProductVariantKeys.detail(id),
      });

      return data;
    },
  });
};
