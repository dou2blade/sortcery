import { apiGet } from "@/utils/api";
import { useQuery } from "@tanstack/react-query";
import { UserStats } from "../types/userStats";
import { userKeys } from "./userKeys";

export const useUserStats = () => {
  return useQuery({
    queryKey: userKeys.stats,
    queryFn: () => apiGet<UserStats>("users/stats"),
  });
};
