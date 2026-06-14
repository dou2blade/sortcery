import { Product } from "@/features/products/types";

export interface Brand {
  id: number;
  name: string;
  imageUrl: string;
  products: Pick<Product, "id" | "name">[];
  createdAt: string;
  updatedAt: string;
}
