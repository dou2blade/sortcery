import { LoginFormData } from "@/features/auth/schemas";
import { useAuthStore } from "@/features/auth/stores";
import { LoginResponse } from "@/features/auth/types";
import { apiPost } from "../api";

export const login = async ({ email, password, role }: LoginFormData) => {
  const loginStore = useAuthStore.getState().login;

  const { data, message } = await apiPost<LoginResponse>(
    `auth/login/${role.toLowerCase()}`, 
    { email, password }
  );

  if (!data) {
    return message;
  }

  const { plainToken, ...user } = data;
  loginStore(plainToken, user);

  return null;
}
