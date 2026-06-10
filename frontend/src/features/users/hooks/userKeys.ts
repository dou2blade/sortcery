import { UserQueryParams } from "../types";

export const userKeys = {
  all: ["users"] as const,

  lists: () => [...userKeys.all, "list"] as const,
  list: (params: UserQueryParams) => [...userKeys.lists(), params] as const,

  detail: (id: number) => [...userKeys.all, "detail", id] as const,

  options: () => [...userKeys.all, "options"] as const,
  stats: () => [...userKeys.all, "stats"] as const
};
