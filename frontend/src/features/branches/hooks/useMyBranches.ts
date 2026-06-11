import { useQuery } from "@tanstack/react-query"
import { apiGet } from "@/utils/api";
import { branchKeys } from "./branchKeys";
import { Branch } from "../types";

export const useMyBranches = () => {
  return useQuery({
    queryKey: branchKeys.my(),
    queryFn: async () => await apiGet<Branch[]>("my/branches")
  });
};
