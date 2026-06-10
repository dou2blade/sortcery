import { useQuery } from "@tanstack/react-query"
import { apiGet } from "@/utils/api";
import { storeKeys } from "./storeKeys";
import { Store } from "../types";

export const useStore = (id: number) => {
  return useQuery({
    queryKey: storeKeys.detail(id),
    queryFn: async () => await apiGet<Store>(`stores/${id}`)
  });
};
