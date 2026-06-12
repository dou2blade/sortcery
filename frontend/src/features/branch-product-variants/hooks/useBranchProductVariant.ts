import { useQuery } from "@tanstack/react-query"
import { apiGet } from "@/utils/api";
import { branchProductVariantKeys } from "./branchProductVariantKeys";
import { BranchProductVariant } from "../types";

export const useBranchProductVariant = (id: number, branch?: number) => {
  return useQuery({
    queryKey: branchProductVariantKeys.detail(id),
    queryFn: async () => await apiGet<BranchProductVariant>(`branch-product-variants/${id}`, { branch }),
    enabled: branch !== undefined
  });
};
