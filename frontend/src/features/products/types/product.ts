export interface Product {
  id: number;
  productCategoryId: number;
  brandId: number;
  name: string;
  productVariants: Record<string, any>[],
  createdAt: string;
  updatedAt: string;
}
