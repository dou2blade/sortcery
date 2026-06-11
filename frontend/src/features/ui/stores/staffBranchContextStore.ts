import { create } from "zustand";

type BranchContextState = {
  branchId?: number;
  setBranchId: (id: number) => void;
  clearBranchId: () => void;

  name?: string;
  setName: (name: string) => void;
};

export const useStaffBranchContextStore = create<BranchContextState>((set) => ({
  branchId: undefined,
  setBranchId: (branchId) => set({ branchId }),
  clearBranchId: () => set({ branchId: undefined }),

  name: undefined,
  setName: (name) => set({ name }),
}));
