import { useMutation, useQueryClient } from "@tanstack/react-query";
import { apiPut } from "@/utils/api";
import { inventoryMovementKeys } from "./inventoryMovementKeys";
import { InventoryMovementFormData } from "../schemas";

export const useUpdateInventoryMovement = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: ({ id, payload }: { 
      id: number, 
      payload: InventoryMovementFormData 
    }) => apiPut(`inventory-movements/${id}`, payload),

    onSuccess: (data, { id }) => {
      queryClient.invalidateQueries({
        queryKey: inventoryMovementKeys.lists(),
      });

      queryClient.invalidateQueries({
        queryKey: inventoryMovementKeys.detail(id),
      });

      return data;
    },
  });
};
