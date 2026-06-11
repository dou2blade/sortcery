import { useMutation, useQueryClient } from "@tanstack/react-query";
import { apiPost } from "@/utils/api";
import { brandKeys } from "./brandKeys";
import { BrandFormData } from "../schemas";

export const useCreateBrand = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: (payload: BrandFormData) => apiPost("brands", payload),
    onSuccess: (data) => {
      queryClient.invalidateQueries({
        queryKey: brandKeys.all,
      });

      return data;
    },
  });
};
