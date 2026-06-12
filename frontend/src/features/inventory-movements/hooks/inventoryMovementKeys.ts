import { InventoryMovementQueryParams } from "../types";

export const inventoryMovementKeys = {
  all: ["inventory-movements"] as const,

  lists: () => [...inventoryMovementKeys.all, "list"] as const,
  list: (params: InventoryMovementQueryParams) => [...inventoryMovementKeys.lists(), params] as const,

  detail: (id: number, branch: number) => [...inventoryMovementKeys.all, "detail", branch, id] as const,
};
