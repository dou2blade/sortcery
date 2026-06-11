import { Product } from "@/features/products/types";

export interface ProductCategory {
  id: number;
  name: string;
  products: Pick<Product, "id" | "name">[];
  createdAt: string;
  updatedAt: string;
}
