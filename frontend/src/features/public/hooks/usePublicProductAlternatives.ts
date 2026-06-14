import { useQuery } from "@tanstack/react-query"
import { apiGet } from "@/utils/api";
import { publicKeys } from "./publicKeys";
import { PublicBranchProduct } from "../types";

export const usePublicProductAlternatives = (id: number, latitude: number, longitude: number) => {
  return useQuery({
    queryKey: publicKeys.productsAlternatives(id),
    queryFn: async () => await apiGet<PublicBranchProduct[]>(`public/products/variants/${id}`, { latitude, longitude })
  });
};
