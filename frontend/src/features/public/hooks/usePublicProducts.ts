import { useInfiniteQuery } from "@tanstack/react-query";
import { PublicBranchProduct } from "../types";
import { publicKeys } from "./publicKeys";
import { apiGet, ApiResponse } from "@/utils/api";
import { PublicProductParams } from "../types";

export const usePublicProducts = (params: PublicProductParams) => {
  return useInfiniteQuery({
    queryKey: publicKeys.productsQuery(params),

    queryFn: ({ pageParam = 0 }) => apiGet<PublicBranchProduct[]>("public/products", { ...params, page: pageParam }),

    initialPageParam: 0,

    getNextPageParam: (lastPage: ApiResponse<PublicBranchProduct[]>) => {
      if (lastPage.meta?.last) {
        return undefined;
      }

      return (lastPage.meta?.page ?? 0) + 1;
    },
  });
};

