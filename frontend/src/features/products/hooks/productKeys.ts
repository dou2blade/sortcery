import { ProductQueryParams } from "../types";

export const productKeys = {
  all: ["products"] as const,

  lists: () => [...productKeys.all, "list"] as const,
  list: (params: ProductQueryParams) => [...productKeys.lists(), params] as const,

  detail: (id: number) => [...productKeys.all, "detail", id] as const,

  options: () => [...productKeys.all, "options"] as const,
  stats: () => [...productKeys.all, "stats"] as const,
};
