import { useMutation, useQueryClient } from "@tanstack/react-query";
import { apiPost } from "@/utils/api";
import { branchProductVariantKeys } from "./branchProductVariantKeys";
import { BranchProductVariantFormData } from "../schemas";

export const useCreateBranchProductVariant = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: (payload: BranchProductVariantFormData) => apiPost("branch-product-variants", payload),
    onSuccess: (data) => {
      queryClient.invalidateQueries({
        queryKey: branchProductVariantKeys.all,
      });

      return data;
    },
  });
};
