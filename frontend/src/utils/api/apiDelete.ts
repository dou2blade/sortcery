import { apiFetch } from "@/utils/api/apiFetch"

export const apiDelete = async (resource: string) => {
  return await apiFetch({
    method: "DELETE",
    resource: resource,
  });
}
