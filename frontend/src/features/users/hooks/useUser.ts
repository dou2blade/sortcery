import { useQuery } from "@tanstack/react-query"
import { apiGet } from "@/utils/api";
import { userKeys } from "./userKeys";
import { User } from "../types";

export const useUser = (id: number) => {
  return useQuery({
    queryKey: userKeys.detail(id),
    queryFn: async () => await apiGet<User>(`users/${id}`)
  });
};
