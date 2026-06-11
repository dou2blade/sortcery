import { Branch } from "@/features/branches/types";

export interface Store {
  id: number;
  name: string;
  branches: Pick<Branch, "id" | "storeId" | "name" | "storeName">[];
  createdAt: string;
  updatedAt: string;
}
