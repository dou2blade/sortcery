import { useQuery } from "@tanstack/react-query";
import { PublicProduct } from "../types";
import { publicKeys } from "./publicKeys";
import { apiGet } from "@/utils/api";

export const usePublicProductsTopNearby = (latitude?: number, longitude?: number) => {
  return useQuery({
    queryKey: publicKeys.productsTopNearby(),
    queryFn: () => apiGet<PublicProduct[]>("public/products/top", { size: 15, latitude, longitude }),
    enabled: latitude !== undefined && longitude !== undefined
  });
};
