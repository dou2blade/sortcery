import { BranchProductVariantQueryParams } from "../types";

export const branchProductVariantKeys = {
  all: ["branch-product-variants"] as const,

  lists: () => [...branchProductVariantKeys.all, "list"] as const,
  list: (params: BranchProductVariantQueryParams) => [...branchProductVariantKeys.lists(), params] as const,

  detail: (id: number) => [...branchProductVariantKeys.all, "detail", id] as const,

  options: () => [...branchProductVariantKeys.all, "options"] as const,
};
