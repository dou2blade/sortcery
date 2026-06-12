import * as z from "zod";
import { InventoryMovement } from "../types";

export const inventoryMovementDelta = {
  STOCK_IN: 1,
  TRANSFER_IN: 1,
  ADJUSTMENT_IN: 1,
  RETURN: 1,

  TRANSFER_OUT: -1,
  ADJUSTMENT_OUT: -1,
  SALE: -1,
  DAMAGED: -1,
  EXPIRED: -1,
} as const satisfies Record<InventoryMovement["type"], 1 | -1>;

export const inventoryMovementType = Object.keys(
  inventoryMovementDelta
) as [InventoryMovement["type"], ...InventoryMovement["type"][]];

export const InventoryMovementSchema = z
  .object({
    branchProductVariantId: z.number().optional()
      .refine((v) => v !== undefined, {
        error: "The product is required",
      }),

    createdById: z.number().optional()
      .refine((v) => v !== undefined, {
        error: "The user is required",
      }),

    type: z.enum(inventoryMovementType).optional()
      .refine((v) => v !== undefined, {
        error: "The type is required",
      }),

    quantityChange: z.coerce.number().optional()
      .refine((v) => v !== undefined, {
        error: "The quantity change is required",
      })
      .refine((v) => v === undefined || v > 0, {
        error: "Quantity change must be positive",
      }),

    notes: z.string(),
  })
  .transform((data) => ({
    ...data,
    quantityChange:
      data.quantityChange! *
      inventoryMovementDelta[data.type!],
  }));

export type InventoryMovementFormData =
  z.input<typeof InventoryMovementSchema>;

export type InventoryMovementPayload =
  z.output<typeof InventoryMovementSchema>;

export const inventoryMovementToFormData = (
  inventoryMovement?: InventoryMovement
): InventoryMovementFormData => ({
  branchProductVariantId:
    inventoryMovement?.branchProductVariantId,

  createdById:
    inventoryMovement?.createdById,

  type:
    inventoryMovement?.type,

  quantityChange:
    inventoryMovement
      ? Math.abs(inventoryMovement.quantityChange)
      : undefined,

  notes:
    inventoryMovement?.notes ?? "",
});
