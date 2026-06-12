import { BranchProductVariantQueryParams } from "../types";

export const branchProductVariantKeys = {
  all: ["branch-product-variants"] as const,

  lists: () => [...branchProductVariantKeys.all, "list"] as const,
  list: (params: BranchProductVariantQueryParams) => [...branchProductVariantKeys.lists(), params] as const,

  detail: (branch: number, id: number) => [...branchProductVariantKeys.all, "detail", branch, id] as const,

  options: (branch: number) => [...branchProductVariantKeys.all, "options", branch] as const,
};
