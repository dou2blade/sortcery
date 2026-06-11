import { useQuery } from "@tanstack/react-query"
import { apiGet } from "@/utils/api";
import { productKeys } from "./productKeys";
import { Product, ProductQueryParams } from "../types";

export const useProducts = (params: ProductQueryParams) => {
  return useQuery({
    queryKey: productKeys.list(params),
    queryFn: async () => await apiGet<Product[]>("products", params)
  });
};
