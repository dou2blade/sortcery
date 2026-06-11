import { useQuery } from "@tanstack/react-query"
import { apiGet } from "@/utils/api";
import { brandKeys } from "./brandKeys";
import { BrandOption } from "../types";

export const useBrandOptions = () => {
  return useQuery({
    queryKey: brandKeys.options(),
    queryFn: async () => await apiGet<BrandOption[]>("brands/options")
  });
};
