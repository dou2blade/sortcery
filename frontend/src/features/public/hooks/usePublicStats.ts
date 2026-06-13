import { apiGet } from "@/utils/api";
import { useQuery } from "@tanstack/react-query";
import { publicKeys } from "./publicKeys";
import { PublicStats } from "../types";

export const usePublicStats = () => {
  return useQuery({
    queryKey: publicKeys.stats(),
    queryFn: () => apiGet<PublicStats>("public/stats"),
  });
};
