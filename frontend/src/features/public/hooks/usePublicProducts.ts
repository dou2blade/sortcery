import { useQuery } from "@tanstack/react-query";
import { PublicBranchProduct } from "../types";
import { publicKeys } from "./publicKeys";
import { apiGet } from "@/utils/api";
import { PublicProductParams } from "../types";

export const usePublicProducts = (params: PublicProductParams) => {
  return useQuery({
    queryKey: publicKeys.productsQuery(params),
    queryFn: () => apiGet<PublicBranchProduct[]>("public/products", params),
  });
};

