import { useQuery } from "@tanstack/react-query"
import { apiGet } from "@/utils/api";
import { branchProductVariantKeys } from "./branchProductVariantKeys";
import { BranchProductVariant, BranchProductVariantQueryParams } from "../types";

export const useBranchProductVariants = (params: BranchProductVariantQueryParams) => {
  return useQuery({
    queryKey: branchProductVariantKeys.list(params),
    queryFn: async () => await apiGet<BranchProductVariant[]>("branch-product-variants", params),
    enabled: params.branch !== undefined
  });
};
