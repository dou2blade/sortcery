import { User } from "@/features/users/types";

export type AuthSession = {
  token: string | null;
  user: User | null;
}
