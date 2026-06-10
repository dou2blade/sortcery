import { apiGet } from "@/utils/api";
import { useQuery } from "@tanstack/react-query";
import { StoreStats } from "../types";
import { storeKeys } from "./storeKeys";

export const useStoreStats = () => {
  return useQuery({
    queryKey: storeKeys.stats(),
    queryFn: () => apiGet<StoreStats>("stores/stats"),
  });
};
