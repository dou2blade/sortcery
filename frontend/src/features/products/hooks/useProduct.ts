import { useQuery } from "@tanstack/react-query"
import { apiGet } from "@/utils/api";
import { productKeys } from "./productKeys";
import { Product } from "../types";

export const useProduct = (id: number) => {
  return useQuery({
    queryKey: productKeys.detail(id),
    queryFn: async () => await apiGet<Product>(`products/${id}`)
  });
};
