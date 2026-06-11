import { useQuery } from "@tanstack/react-query"
import { apiGet } from "@/utils/api";
import { productKeys } from "./productKeys";
import { ProductOption } from "../types";

export const useProductOptions = () => {
  return useQuery({
    queryKey: productKeys.options(),
    queryFn: async () => await apiGet<ProductOption[]>("products/options")
  });
};
