import { useQuery } from "@tanstack/react-query"
import { apiGet } from "@/utils/api";
import { publicKeys } from "./publicKeys";
import { ProductCategory } from "@/features/product-categories/types";

export const usePublicProductCategories = () => {
  return useQuery({
    queryKey: publicKeys.productCategories(),
    queryFn: async () => await apiGet<ProductCategory[]>("public/product-categories")
  });
};
