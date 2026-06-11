import { useQuery } from "@tanstack/react-query"
import { apiGet } from "@/utils/api";
import { brandKeys } from "./brandKeys";
import { Brand } from "../types";

export const useBrand = (id: number) => {
  return useQuery({
    queryKey: brandKeys.detail(id),
    queryFn: async () => await apiGet<Brand>(`brands/${id}`)
  });
};
