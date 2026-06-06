import { apiFetch } from "@/utils/api/apiFetch"

export const apiGet = async <T>(resource: string) => {
  return await apiFetch<T>({ resource });
}
