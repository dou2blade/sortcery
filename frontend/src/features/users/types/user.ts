import { Branch } from "@/features/branches/types";

export interface User {
  id: number;
  firstName: string;
  middleName?: string;
  lastName: string;
  email: string;
  role: "ADMIN" | "MANAGER" | "RETAILER" | "CONSUMER";
  branches: Pick<Branch, "id" | "storeId" | "name" | "storeName">[];
  createdAt: string;
  updatedAt: string;
}
