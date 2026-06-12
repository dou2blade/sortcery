import { useMutation, useQueryClient } from "@tanstack/react-query";
import { apiDelete } from "@/utils/api";
import { branchProductVariantKeys } from "./branchProductVariantKeys";

export const useDeleteBranchProductVariant = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: (id: number) => apiDelete(`branch-product-variants/${id}`),

    onSuccess: (data, id) => {
      queryClient.removeQueries({
        queryKey: branchProductVariantKeys.detail(id),
      });

      queryClient.invalidateQueries({
        queryKey: branchProductVariantKeys.lists(),
      });

      return data;
    },
  });
};
