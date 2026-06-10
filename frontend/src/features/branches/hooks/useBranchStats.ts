import { apiGet } from "@/utils/api";
import { useQuery } from "@tanstack/react-query";
import { BranchStats } from "../types/branchStats";
import { branchKeys } from "./branchKeys";

export const useBranchStats = () => {
  return useQuery({
    queryKey: branchKeys.stats(),
    queryFn: () => apiGet<BranchStats>("branches/stats"),
  });
};
