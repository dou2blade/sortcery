import { useMutation, useQueryClient } from "@tanstack/react-query";
import { apiDelete } from "@/utils/api";
import { branchKeys } from "./branchKeys";

export const useDeleteBranch = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: (id: number) => apiDelete(`branches/${id}`),

    onSuccess: (data, id) => {
      queryClient.removeQueries({
        queryKey: branchKeys.detail(id),
      });

      queryClient.invalidateQueries({
        queryKey: branchKeys.stats(),
      });

      queryClient.invalidateQueries({
        queryKey: branchKeys.lists(),
      });

      return data;
    },
  });
};
