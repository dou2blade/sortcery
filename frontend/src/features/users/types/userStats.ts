import { User } from "./user";

export interface UserStats {
  total: number;
  byRole: Record<User["role"], number>;
}
