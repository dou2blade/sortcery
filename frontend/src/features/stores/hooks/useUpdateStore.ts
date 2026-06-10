import { useMutation, useQueryClient } from "@tanstack/react-query";
import { apiPut } from "@/utils/api";
import { storeKeys } from "./storeKeys";
import { StoreFormData } from "../schemas";

export const useUpdateStore = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: ({ id, payload }: { 
      id: number, 
      payload: StoreFormData 
    }) => apiPut(`stores/${id}`, payload),

    onSuccess: (data, { id }) => {
      queryClient.invalidateQueries({
        queryKey: storeKeys.lists(),
      });

      queryClient.invalidateQueries({
        queryKey: storeKeys.detail(id),
      });

      return data;
    },
  });
};
