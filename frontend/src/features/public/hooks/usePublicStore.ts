import { useQuery } from "@tanstack/react-query"
import { apiGet } from "@/utils/api";
import { publicKeys } from "./publicKeys";
import { Store } from "@/features/stores/types";

export const usePublicStore = (id: number) => {
  return useQuery({
    queryKey: publicKeys.branch(id),
    queryFn: async () => await apiGet<Store>(`public/stores/${id}`),
  });
}
