import { useQuery } from "@tanstack/react-query"
import { apiGet } from "@/utils/api";
import { publicKeys } from "./publicKeys";
import { PublicBranch } from "../types/publicBranch";

export const usePublicBranchesNearby = (latitude?: number, longitude?: number) => {
  return useQuery({
    queryKey: publicKeys.branchesNearby(),
    queryFn: async () => await apiGet<PublicBranch[]>("public/branches/nearby", { latitude, longitude }),
    enabled: latitude !== undefined && longitude !== undefined
  });
};
