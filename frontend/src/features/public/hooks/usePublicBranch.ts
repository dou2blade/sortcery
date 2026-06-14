import { useQuery } from "@tanstack/react-query"
import { apiGet } from "@/utils/api";
import { publicKeys } from "./publicKeys";
import { PublicBranch } from "../types/publicBranch";

export const usePublicBranch = (id: number, latitude: string, longitude: string) => {
  return useQuery({
    queryKey: publicKeys.branch(id),
    queryFn: async () => await apiGet<PublicBranch>(`public/branches/${id}`, { latitude, longitude }),
  });
};
