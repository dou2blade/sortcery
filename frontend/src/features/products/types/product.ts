import { ProductVariant } from "@/features/product-variants/types";

export interface Product {
  id: number;
  productCategoryId: number;
  brandId: number;
  name: string;
  productVariants: ProductVariant[],
  createdAt: string;
  updatedAt: string;
}
