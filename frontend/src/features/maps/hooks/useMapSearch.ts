import { useQuery } from "@tanstack/react-query";
import { apiGet } from "@/utils/api";
import { mapKeys } from "./mapKeys";
import { MapAddressSuggestion } from "../types/mapAddressSuggestion";

export const useMapSearch = (query: string) => {
  return useQuery({
    queryKey: mapKeys.search(query),
    queryFn: () => apiGet<MapAddressSuggestion[]>("maps/search", { query }),
    enabled: query.length >= 3,
  });
};
