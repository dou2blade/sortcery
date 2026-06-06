export const apiFetch = async <T>({ resource, method, body }: {
  resource: string;
  method?: "GET" | "POST" | "PUT" | "DELETE";
  body?: object;
}) => {
  const apiUrl = process.env.EXPO_PUBLIC_API_URL;
  const res = await fetch(`${apiUrl}${resource}`, {
    method: method ?? "GET",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify(body)
  });

  return await res.json() as {
    status?: number,
    message?: string,
    data?: T, 
    timestamp: string, 
    meta?: {
      page: number,
      size: number,
      totalElements: number,
      totalPages: number,
      last: boolean
    }
  };
}
