import { useQuery } from "@tanstack/react-query"
import { apiGet } from "@/utils/api";
import { inventoryMovementKeys } from "./inventoryMovementKeys";
import { InventoryMovement } from "../types";

export const useInventoryMovement = (id: number, branch?: number) => {
  return useQuery({
    queryKey: inventoryMovementKeys.detail(branch!, id),
    queryFn: async () => await apiGet<InventoryMovement>(`inventory-movements/${id}`, { branch }),
    enabled: branch !== undefined
  });
};
