import { useQuery } from "@tanstack/react-query"
import { apiGet } from "@/utils/api";
import { productVariantKeys } from "./productVariantKeys";
import { ProductVariant } from "../types";

export const useProductVariant = (id: number) => {
  return useQuery({
    queryKey: productVariantKeys.detail(id),
    queryFn: async () => await apiGet<ProductVariant>(`product-variants/${id}`)
  });
};
