import { useQuery } from "@tanstack/react-query"
import { apiGet } from "@/utils/api";
import { inventoryMovementKeys } from "./inventoryMovementKeys";
import { InventoryMovement, InventoryMovementQueryParams } from "../types";

export const useInventoryMovements = (params: InventoryMovementQueryParams) => {
  return useQuery({
    queryKey: inventoryMovementKeys.list(params),
    queryFn: async () => await apiGet<InventoryMovement[]>("inventory-movements", params),
    enabled: params.branch !== undefined
  });
};
