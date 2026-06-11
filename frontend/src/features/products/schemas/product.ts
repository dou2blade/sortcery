import * as z from "zod";
import { Product } from "../types";

export type ProductFormData = z.infer<typeof ProductSchema>;

export const ProductSchema = z.object({
  name: z.string().min(1, { error: "The name is required" }),
  productCategoryId: z.number().optional()
    .refine((v) => v !== undefined, { error: "The category is required" }),
  brandId: z.number().optional()
    .refine((v) => v !== undefined, { error: "The brandId is required" }),
})

export const productToFormData = (product?: Product): ProductFormData => ({
  name: product?.name ?? "",
  productCategoryId: product?.productCategoryId,
  brandId: product?.brandId,
});
