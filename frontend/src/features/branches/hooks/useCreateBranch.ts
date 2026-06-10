import { useMutation, useQueryClient } from "@tanstack/react-query";
import { apiPost } from "@/utils/api";
import { branchKeys } from "./branchKeys";
import { BranchFormData } from "../schemas";

export const useCreateBranch = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: (data: BranchFormData) => apiPost("branches", data),
    onSuccess: (data) => {
      queryClient.invalidateQueries({
        queryKey: branchKeys.all,
      });

      return data;
    },
  });
};
