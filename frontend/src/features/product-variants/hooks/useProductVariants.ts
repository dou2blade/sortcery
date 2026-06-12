import { useQuery } from "@tanstack/react-query"
import { apiGet } from "@/utils/api";
import { productVariantKeys } from "./productVariantKeys";
import { ProductVariant, ProductVariantQueryParams } from "../types";

export const useProductVariants = (params: ProductVariantQueryParams) => {
  return useQuery({
    queryKey: productVariantKeys.list(params),
    queryFn: async () => await apiGet<ProductVariant[]>("product-variants", params)
  });
};
