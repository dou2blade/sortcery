import { useQuery } from "@tanstack/react-query"
import { apiGet } from "@/utils/api";
import { storeKeys } from "./storeKeys";
import { Store, StoreQueryParams } from "../types";

export const useStores = (params: StoreQueryParams) => {
  return useQuery({
    queryKey: storeKeys.list(params),
    queryFn: async () => await apiGet<Store[]>("stores", params)
  });
};
