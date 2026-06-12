import { useQuery } from "@tanstack/react-query"
import { apiGet } from "@/utils/api";
import { productVariantKeys } from "./productVariantKeys";
import { ProductVariantOption } from "../types";

export const useProductVariantOptions = () => {
  return useQuery({
    queryKey: productVariantKeys.options(),
    queryFn: async () => await apiGet<ProductVariantOption[]>("product-variants/options")
  });
};
