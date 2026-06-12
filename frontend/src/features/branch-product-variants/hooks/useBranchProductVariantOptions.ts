import { useQuery } from "@tanstack/react-query"
import { apiGet } from "@/utils/api";
import { branchProductVariantKeys } from "./branchProductVariantKeys";
import { BranchProductVariantOption } from "../types";

export const useBranchProductVariantOptions = (branch?: number) => {
  return useQuery({
    queryKey: branchProductVariantKeys.options(branch!),
    queryFn: async () => await apiGet<BranchProductVariantOption[]>("branch-product-variants/options", { branch }),
    enabled: branch !== undefined
  });
};
