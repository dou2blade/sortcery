import { useQuery } from "@tanstack/react-query"
import { apiGet } from "@/utils/api";
import { publicKeys } from "./publicKeys";
import { ProductCategoryOption } from "@/features/product-categories/types";

export const usePublicProductCategoryOptions = () => {
  return useQuery({
    queryKey: publicKeys.productCategoryOptions(),
    queryFn: async () => await apiGet<ProductCategoryOption[]>("public/product-categories/options")
  });
};
