import * as z from "zod";
import { BranchProductVariant } from "../types";

export type BranchProductVariantFormData = z.infer<typeof BranchProductVariantSchema>;

export const BranchProductVariantSchema = z.object({
  productVariantId: z.number().optional()
    .refine((v) => v !== undefined, { error: "The product variant is required" }),
  sku: z.string().min(1, { error: "The stock keeping unit is required" }),
  price: z.number().min(0, { error: "The price must be greater than or equal to zero" })
    .optional()
    .refine((v) => v !== undefined, { error: "The price is required" }),
  quantity: z.number().min(0, { error: "The quantity must be greater than or equal to zero" })
    .optional()
    .refine((v) => v !== undefined, { error: "The quantity is required" })
});

export const branchProductVariantToFormData = (branchProductVariant?: BranchProductVariant): BranchProductVariantFormData => ({
  productVariantId: branchProductVariant?.productVariantId,
  sku: branchProductVariant?.sku ?? "",
  price: branchProductVariant?.price,
  quantity: branchProductVariant?.quantity
});
