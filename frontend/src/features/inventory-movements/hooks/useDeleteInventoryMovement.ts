import { useMutation, useQueryClient } from "@tanstack/react-query";
import { apiDelete } from "@/utils/api";
import { inventoryMovementKeys } from "./inventoryMovementKeys";

export const useDeleteInventoryMovement = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: (id: number) => apiDelete(`inventory-movements/${id}`),

    onSuccess: (data, id) => {
      queryClient.removeQueries({
        queryKey: inventoryMovementKeys.detail(id),
      });

      queryClient.invalidateQueries({
        queryKey: inventoryMovementKeys.lists(),
      });

      return data;
    },
  });
};
