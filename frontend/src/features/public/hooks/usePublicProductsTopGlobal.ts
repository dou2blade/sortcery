import { useQuery } from "@tanstack/react-query";
import { PublicProduct } from "../types";
import { publicKeys } from "./publicKeys";
import { apiGet } from "@/utils/api";

export const usePublicProductsTopGlobal = () => {
  return useQuery({
    queryKey: publicKeys.productsTopGlobal(),
    queryFn: () => apiGet<PublicProduct[]>("public/products/top", { size: 15, page: 0 }),
  });
};
