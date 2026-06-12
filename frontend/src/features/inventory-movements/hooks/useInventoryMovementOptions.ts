import { useQuery } from "@tanstack/react-query"
import { apiGet } from "@/utils/api";
import { inventoryMovementKeys } from "./inventoryMovementKeys";
import { InventoryMovementOption } from "../types";

export const useInventoryMovementOptions = () => {
  return useQuery({
    queryKey: inventoryMovementKeys.options(),
    queryFn: async () => await apiGet<InventoryMovementOption[]>("inventory-movements/options")
  });
};
