import { useQuery } from "@tanstack/react-query"
import { apiGet } from "@/utils/api";
import { branchKeys } from "./branchKeys";
import { Branch, BranchQueryParams } from "../types";

export const useBranches = (params: BranchQueryParams) => {
  return useQuery({
    queryKey: branchKeys.list(params),
    queryFn: async () => await apiGet<Branch[]>("branches", params)
  });
};
