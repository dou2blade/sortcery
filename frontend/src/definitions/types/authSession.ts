import { User } from "@/definitions/schemas";

export type AuthSession = {
  token: string | null;
  user: User | null;
}
