import * as z from "zod";
import { ProductCategory } from "../types";

export type ProductCategoryFormData = z.infer<typeof ProductCategorySchema>;

export const ProductCategorySchema = z.object({
  name: z.string().min(1, { error: "The name is required" })
});

export const productCategoryToFormData = (productCategory?: ProductCategory): ProductCategoryFormData => ({
  name: productCategory?.name ?? ""
});
