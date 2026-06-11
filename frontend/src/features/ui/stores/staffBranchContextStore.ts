import { Branch } from "@/features/branches/types";
import { create } from "zustand";

type BranchContextState = {
  branch?: Branch;
  setBranch: (branch: Branch) => void;
};

export const useStaffBranchContextStore = create<BranchContextState>((set) => ({
  branch: undefined,
  setBranch: (branch) => set({ branch })
}));
