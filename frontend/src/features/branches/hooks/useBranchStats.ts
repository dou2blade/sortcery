import { apiGet } from "@/utils/api";
import { useQuery } from "@tanstack/react-query";
import { BranchStats } from "../types";
import { branchKeys } from "./branchKeys";

export const useBranchStats = (id?: number) => {
  return useQuery({
    queryKey: branchKeys.stat(id!),
    queryFn: async () => apiGet<BranchStats>(`branches/${id}/stats`),
    enabled: id !== undefined,
  });
};
