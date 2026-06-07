import { User } from "@/features/users/types";

export interface LoginResponse extends User {
  plainToken: string;
}
