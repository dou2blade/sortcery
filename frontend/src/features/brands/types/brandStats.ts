import { Brand } from "./brand";

export interface BrandStats {
  total: number;
  byRole: Record<Brand["role"], number>;
}
