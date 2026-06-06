import { User } from "@/definitions/types/user";

export type AuthSession = {
  token: string | null;
  user: User | null;
}
