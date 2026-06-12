import * as z from "zod";
import { ProductVariant } from "../types";

export type ProductVariantFormData = z.infer<typeof ProductVariantSchema>;

export const ProductVariantSchema = z.object({
  productId: z.number().optional()
    .refine((v) => v !== undefined, { error: "The product is required" }),
  name: z.string().min(1, { error: "The name is required" }),
  imageUrl: z.string()
});

export const productVariantToFormData = (productVariant?: ProductVariant): ProductVariantFormData => ({
  productId: productVariant?.productId,
  name: productVariant?.name ?? "",
  imageUrl: productVariant?.imageUrl ?? ""
});
