import { apiFetch } from "@/utils/api/apiFetch"

export const apiPost = async (resource: string, body?: object) => {
  return await apiFetch({
    method: "POST",
    resource: resource,
    body: body
  });
}
