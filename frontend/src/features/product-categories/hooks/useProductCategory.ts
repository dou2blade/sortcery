import { useQuery } from "@tanstack/react-query"
import { apiGet } from "@/utils/api";
import { productCategoryKeys } from "./productCategoryKeys";
import { ProductCategory } from "../types";

export const useProductCategory = (id: number) => {
  return useQuery({
    queryKey: productCategoryKeys.detail(id),
    queryFn: async () => await apiGet<ProductCategory>(`product-categories/${id}`)
  });
};
