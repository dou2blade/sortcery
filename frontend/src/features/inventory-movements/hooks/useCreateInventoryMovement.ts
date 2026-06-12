import { useMutation, useQueryClient } from "@tanstack/react-query";
import { apiPost } from "@/utils/api";
import { inventoryMovementKeys } from "./inventoryMovementKeys";
import { InventoryMovementFormData } from "../schemas";
import { branchProductVariantKeys } from "@/features/branch-product-variants/hooks";

export const useCreateInventoryMovement = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: (payload: InventoryMovementFormData) => apiPost("inventory-movements", payload),
    onSuccess: (data) => {
      queryClient.invalidateQueries({
        queryKey: inventoryMovementKeys.all,
      });

      queryClient.invalidateQueries({
        queryKey: branchProductVariantKeys.all
      });

      return data;
    },
  });
};
