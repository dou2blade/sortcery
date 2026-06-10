import { useQuery } from "@tanstack/react-query"
import { apiGet } from "@/utils/api";
import { userKeys } from "./userKeys";
import { UserOption } from "../types";

export const useUserOptions = () => {
  return useQuery({
    queryKey: userKeys.options(),
    queryFn: async () => await apiGet<{ 
      RETAILER: UserOption[]; 
      MANAGER: UserOption[] 
    }>("users/options")
  });
};
