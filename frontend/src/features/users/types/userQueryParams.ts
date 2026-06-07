import { User } from "./user";

export interface UserQueryParams {
  page: number;
  role: User["role"];
  search: string;
}
