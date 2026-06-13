import { apiGet } from "@/utils/api";
import { useQuery } from "@tanstack/react-query";
import { productKeys } from "./productKeys";
import { PublicProduct } from "@/features/public/types";

export const useProductTopSellers = () => {
  return useQuery({
    queryKey: productKeys.topSellers(),
    queryFn: () => apiGet<PublicProduct[]>("public/products/top-sellers", { size: 15 }),
  });
};
