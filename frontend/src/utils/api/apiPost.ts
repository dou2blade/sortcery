import { apiFetch } from "@/utils/api/apiFetch"

export const apiPost = async <T>(resource: string, body?: object) => {
  return await apiFetch<T>({
    method: "POST",
    resource: resource,
    body: body
  });
}
