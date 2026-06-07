import { apiFetch } from "@/utils/api/apiFetch"

export const apiGet = async <T>(
  resource: string, 
  params?: Record<string, any>
) => {
  return await apiFetch<T>({ resource, params });
}
