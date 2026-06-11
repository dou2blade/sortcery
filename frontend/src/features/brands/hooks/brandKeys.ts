import { BrandQueryParams } from "../types";

export const brandKeys = {
  all: ["brands"] as const,

  lists: () => [...brandKeys.all, "list"] as const,
  list: (params: BrandQueryParams) => [...brandKeys.lists(), params] as const,

  detail: (id: number) => [...brandKeys.all, "detail", id] as const,

  options: () => [...brandKeys.all, "options"] as const,
  stats: () => [...brandKeys.all, "stats"] as const
};
