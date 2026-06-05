import { apiFetch } from "@/utils/api/apiFetch"

export const apiGet = async (resource: string) => {
  return await apiFetch({ resource });
}
