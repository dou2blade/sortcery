import { StoreQueryParams } from "../types";

export const storeKeys = {
  all: ["stores"] as const,

  lists: () => [...storeKeys.all, "list"] as const,

  list: (params: StoreQueryParams) => [...storeKeys.lists(), params] as const,

  stats: () => [...storeKeys.all, "stats"] as const,

  detail: (id: number) => [...storeKeys.all, "detail", id] as const,
};
