import { apiFetch } from "@/utils/api/apiFetch"

export const apiPut = async <T>(resource: string, body?: object) => {
  return await apiFetch<T>({
    method: "PUT",
    resource: resource,
    body: body
  });
}
