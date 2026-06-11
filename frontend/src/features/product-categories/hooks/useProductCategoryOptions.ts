import { useQuery } from "@tanstack/react-query"
import { apiGet } from "@/utils/api";
import { productCategoryKeys } from "./productCategoryKeys";
import { ProductCategoryOption } from "../types";

export const useProductCategoryOptions = () => {
  return useQuery({
    queryKey: productCategoryKeys.options(),
    queryFn: async () => await apiGet<ProductCategoryOption[]>("product-categories/options")
  });
};
