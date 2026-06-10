export interface Store {
  id: number;
  name: string;
  branches: Record<string, any>[];
  createdAt: string;
  updatedAt: string;
}
