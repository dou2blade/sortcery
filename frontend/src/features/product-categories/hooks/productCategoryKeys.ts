import { ProductCategoryQueryParams } from "../types";

export const productCategoryKeys = {
  all: ["product-categories"] as const,

  lists: () => [...productCategoryKeys.all, "list"] as const,
  list: (params: ProductCategoryQueryParams) => [...productCategoryKeys.lists(), params] as const,

  detail: (id: number) => [...productCategoryKeys.all, "detail", id] as const,

  options: () => [...productCategoryKeys.all, "options"] as const,
};
