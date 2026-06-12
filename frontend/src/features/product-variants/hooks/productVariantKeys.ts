import { ProductVariantQueryParams } from "../types";

export const productVariantKeys = {
  all: ["product-variants"] as const,

  lists: () => [...productVariantKeys.all, "list"] as const,
  list: (params: ProductVariantQueryParams) => [...productVariantKeys.lists(), params] as const,

  detail: (id: number) => [...productVariantKeys.all, "detail", id] as const,

  options: () => [...productVariantKeys.all, "options"] as const,
};
