import { useInfiniteQuery } from "@tanstack/react-query"
import { apiGet, ApiResponse } from "@/utils/api";
import { publicKeys } from "./publicKeys";
import { Brand } from "@/features/brands/types";

type PublicBrand = Pick<Brand, "id" | "name" | "imageUrl">;

export const usePublicBrands = (search: string) => {
  return useInfiniteQuery({
    queryKey: publicKeys.brandsQuery(search),

    queryFn: ({ pageParam = 0 }) => apiGet<PublicBrand[]>("public/brands", { search, page: pageParam }),

    initialPageParam: 0,

    getNextPageParam: (lastPage: ApiResponse<PublicBrand[]>) => {
      if (lastPage.meta?.last) {
        return undefined;
      }

      return (lastPage.meta?.page ?? 0) + 1;
    },
  });
};
