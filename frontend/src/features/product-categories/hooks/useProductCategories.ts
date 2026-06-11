import { useQuery } from "@tanstack/react-query"
import { apiGet } from "@/utils/api";
import { productCategoryKeys } from "./productCategoryKeys";
import { ProductCategory, ProductCategoryQueryParams } from "../types";

export const useProductCategories = (params: ProductCategoryQueryParams) => {
  return useQuery({
    queryKey: productCategoryKeys.list(params),
    queryFn: async () => await apiGet<ProductCategory[]>("product-categories", params)
  });
};
