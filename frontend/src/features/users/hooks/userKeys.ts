import { UserQueryParams } from "../types";

export const userKeys = {
  all: ["users"] as const,
  list: (params: UserQueryParams) => ["users", params] as const,
  detail: (id: number) => ["users", id] as const,
};
