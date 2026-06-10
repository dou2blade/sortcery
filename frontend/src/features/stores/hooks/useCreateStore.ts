import { useMutation, useQueryClient } from "@tanstack/react-query";
import { apiPost } from "@/utils/api";
import { storeKeys } from "./storeKeys";
import { StoreFormData } from "../schemas";

export const useCreateStore = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: (data: StoreFormData) => apiPost("stores", data),
    onSuccess: (data) => {
      queryClient.invalidateQueries({
        queryKey: storeKeys.all,
      });

      return data;
    },
  });
};
