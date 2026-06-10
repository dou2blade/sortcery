import { useQuery } from "@tanstack/react-query"
import { apiGet } from "@/utils/api";
import { storeKeys } from "./storeKeys";
import { StoreOption } from "../types";

export const useStoreOptions = () => {
  return useQuery({
    queryKey: storeKeys.options(),
    queryFn: async () => await apiGet<StoreOption[]>("stores/options")
  });
};
