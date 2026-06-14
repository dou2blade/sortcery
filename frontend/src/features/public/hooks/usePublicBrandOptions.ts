import { useQuery } from "@tanstack/react-query"
import { apiGet } from "@/utils/api";
import { publicKeys } from "./publicKeys";
import { BrandOption } from "@/features/brands/types";

export const usePublicBrandOptions = () => {
  return useQuery({
    queryKey: publicKeys.brandOptions(),
    queryFn: async () => await apiGet<BrandOption[]>("public/brands/options")
  });
};
