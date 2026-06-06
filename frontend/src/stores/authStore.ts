import { create } from "zustand";
import { persist } from "zustand/middleware";

import { AuthSession } from "@/definitions/types/authSession";
import { User } from "@/definitions/types/user";

interface AuthState extends AuthSession {
  login: (token: string, user: User) => void;
  logout: () => void;
}

export const useAuthStore = create<AuthState>()(
  persist(
    (set) => ({
      token: null,
      user: null,

      login: (token, user) => set({ token, user }),
      logout: () => set({ token: null, user:null })
    }),
    { name: "auth-storage" }
  )
);
