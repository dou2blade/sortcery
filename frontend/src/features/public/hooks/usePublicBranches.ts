import { useInfiniteQuery } from "@tanstack/react-query"
import { apiGet, ApiResponse } from "@/utils/api";
import { publicKeys } from "./publicKeys";
import { PublicBranch } from "../types/publicBranch";

export const usePublicBranches = (search: string, store: number, latitude: number, longitude: number) => {
  return useInfiniteQuery({
    queryKey: publicKeys.branchesStore(search, store, latitude, longitude),

    queryFn: ({ pageParam = 0 }) => apiGet<PublicBranch[]>("public/branches", { search, store, latitude, longitude, page: pageParam }),

    initialPageParam: 0,

    getNextPageParam: (lastPage: ApiResponse<PublicBranch[]>) => {
      if (lastPage.meta?.last) {
        return undefined;
      }

      return (lastPage.meta?.page ?? 0) + 1;
    },
  });
};
