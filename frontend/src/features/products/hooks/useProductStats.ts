import { apiGet } from "@/utils/api";
import { useQuery } from "@tanstack/react-query";
import { ProductStats } from "../types";
import { productKeys } from "./productKeys";

export const useProductStats = () => {
  return useQuery({
    queryKey: productKeys.stats(),
    queryFn: () => apiGet<ProductStats>("products/stats"),
  });
};
