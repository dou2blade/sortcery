import { useInfiniteQuery } from "@tanstack/react-query"
import { apiGet, ApiResponse } from "@/utils/api";
import { publicKeys } from "./publicKeys";
import { Store } from "@/features/stores/types";

export const usePublicStores = (search: string) => {
  return useInfiniteQuery({
    queryKey: publicKeys.brandsQuery(search),

    queryFn: ({ pageParam = 0 }) => apiGet<Store[]>("public/stores", { search, page: pageParam }),

    initialPageParam: 0,

    getNextPageParam: (lastPage: ApiResponse<Store[]>) => {
      if (lastPage.meta?.last) {
        return undefined;
      }

      return (lastPage.meta?.page ?? 0) + 1;
    },
  });
};
