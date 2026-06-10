import { useQuery } from "@tanstack/react-query"
import { apiGet } from "@/utils/api";
import { branchKeys } from "./branchKeys";
import { Branch } from "../types";

export const useBranch = (id: number) => {
  return useQuery({
    queryKey: branchKeys.detail(id),
    queryFn: async () => await apiGet<Branch>(`branches/${id}`)
  });
};
