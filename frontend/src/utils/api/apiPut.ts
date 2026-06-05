import { apiFetch } from "@/utils/api/apiFetch"

export const apiPut = async (resource: string, body?: object) => {
  return await apiFetch({
    method: "PUT",
    resource: resource,
    body: body
  });
}
