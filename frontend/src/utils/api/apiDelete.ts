import { apiFetch } from "@/utils/api/apiFetch"

export const apiDelete = async <T>(resource: string) => {
  return await apiFetch<T>({
    method: "DELETE",
    resource: resource,
  });
}
