import { useAuthStore } from "@/features/auth/stores"
import { apiPost } from "../api";

export const logout = async () => {
  const logoutStore = useAuthStore.getState().logout;

  await apiPost("auth/logout");

  logoutStore();
}
