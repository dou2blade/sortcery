import * as z from "zod";
import { Branch } from "../types";

export type BranchFormData = z.infer<typeof BranchSchema>;

export const BranchSchema = z.object({
  name: z.string().min(1, { error: "The name is required" }),
  storeId: z.number().optional()
    .refine((v) => v !== undefined, { error: "The store is required" }),
  address: z.string(),
  latitude: z.number().optional()
    .refine((v) => v !== undefined, { error: "The latitude is required" }),
  longitude: z.number().optional()
    .refine((v) => v !== undefined, { error: "The longitude is required" }),
  managerIds: z.array(z.number()),
  retailerIds: z.array(z.number())
});

export const branchToFormData = (branch?: Branch): BranchFormData => ({
  name: branch?.name ?? "",
  storeId: branch?.storeId,
  address: branch?.address ?? "",
  latitude: branch?.latitude,
  longitude: branch?.longitude,
  managerIds: branch?.managerIds ?? [],
  retailerIds: branch?.retailerIds ?? []
});
