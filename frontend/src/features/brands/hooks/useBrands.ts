import { useQuery } from "@tanstack/react-query"
import { apiGet } from "@/utils/api";
import { brandKeys } from "./brandKeys";
import { Brand, BrandQueryParams } from "../types";

export const useBrands = (params: BrandQueryParams) => {
  return useQuery({
    queryKey: brandKeys.list(params),
    queryFn: async () => await apiGet<Brand[]>("brands", params)
  });
};
