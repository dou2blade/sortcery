import { useAuthStore } from "@/features/auth/stores";
import { ApiResponse } from "./types";

export const apiFetch = async <T>({ resource, method, body, params }: {
  resource: string;
  method?: "GET" | "POST" | "PUT" | "DELETE";
  params?: Record<string, any>;
  body?: object;
}) => {
  const token = useAuthStore.getState().token;

  const apiUrl = process.env.EXPO_PUBLIC_API_URL;

  const queryParams = Object.entries(params ?? {})
    .filter(([_, v]) => v !== undefined && v !== null && (typeof v !== "number" || !isNaN(v)))
    .map(([k, v]) => `${k}=${v}`)
    .join("&");

  const url = `${apiUrl}${resource}${queryParams ? `?${queryParams}` : ""}`

  const res = await fetch(url, {
    method: method ?? "GET",
    headers: {
      "Content-Type": "application/json",
      ...(token && { Authorization: `Bearer ${token}` })
    },
    body: JSON.stringify(body)
  });

  return await res.json() as ApiResponse<T>;
}
