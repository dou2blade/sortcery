import { useQuery } from "@tanstack/react-query"
import { apiGet } from "@/utils/api";
import { userKeys } from "./userKeys";
import { UserOption } from "../types";

export const useUserOptions = (search?: string) => {
  return useQuery({
    queryKey: userKeys.option(search ?? ""),

    queryFn: async () => await apiGet<{ 
      RETAILER: UserOption[]; 
      MANAGER: UserOption[] 
    }>("users/options", { search })
  });
};
