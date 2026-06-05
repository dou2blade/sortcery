export const apiFetch = async ({ resource, method, body }: {
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

  if (!res.ok) {
    let message = "Request failed";
    try {
      const data = await res.json();
      message = data.message ?? message;
    } catch {}
    throw new Error(`${res.status}: ${message}`);
  }

  return await res.json() as {
    status?: number,
    message?: string,
    data?: Record<string, any>, 
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
