import * as z from "zod";
import { Branch } from "../types";

export type ManagerBranchPersonnelFormData = z.infer<typeof ManagerBranchPersonnelSchema>;

export const ManagerBranchPersonnelSchema = z.object({
  managerIds: z.array(z.number()),
  retailerIds: z.array(z.number())
});

export const branchToManagerBranchPersonnelFormData = (branch?: Branch): ManagerBranchPersonnelFormData => ({
  managerIds: branch?.managerIds ?? [],
  retailerIds: branch?.retailerIds ?? []
});
