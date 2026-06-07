import { useQuery } from "@tanstack/react-query"
import { apiGet } from "@/utils/api";
import { userKeys } from "./userKeys";
import { User, UserQueryParams } from "../types";

export const useUsers = (params: UserQueryParams) => {
  return useQuery({
    queryKey: userKeys.list(params),
    queryFn: async () => await apiGet<User[]>("users", params)
  });
};
