import { useMutation, useQueryClient } from "@tanstack/react-query";
import { apiPut } from "@/utils/api";
import { branchKeys } from "./branchKeys";
import { BranchFormData } from "../schemas";
import { userKeys } from "@/features/users/hooks";

export const useUpdateBranch = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: ({ id, payload }: { 
      id: number, 
      payload: BranchFormData 
    }) => apiPut(`branches/${id}`, payload),

    onSuccess: (data, { id }) => {
      queryClient.invalidateQueries({
        queryKey: branchKeys.lists(),
      });

      queryClient.invalidateQueries({
        queryKey: branchKeys.detail(id),
      });

      queryClient.invalidateQueries({
        queryKey: userKeys.details()
      });

      return data;
    },
  });
};
