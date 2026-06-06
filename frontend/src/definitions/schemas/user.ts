export interface User {
  id: number;
  firstName: string;
  middleName?: string;
  lastName: string;
  email: string;
  role: "ADMIN" | "MANAGER" | "RETAILER" | "CONSUMER";
}
