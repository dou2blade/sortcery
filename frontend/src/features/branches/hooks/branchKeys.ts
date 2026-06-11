import { BranchQueryParams } from "../types";

export const branchKeys = {
  all: ["branches"] as const,

  lists: () => [...branchKeys.all, "list"] as const,
  list: (params: BranchQueryParams) => [...branchKeys.lists(), params] as const,

  detail: (id: number) => [...branchKeys.all, "detail", id] as const,

  stats: () => [...branchKeys.all, "stats"] as const,
  stat: (id: number) => [...branchKeys.stats(), "stats", id] as const,

  my: () => [...branchKeys.all, "my"] as const
};
