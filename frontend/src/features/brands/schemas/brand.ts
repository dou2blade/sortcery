import * as z from "zod";
import { Brand } from "../types";

export type BrandFormData = z.infer<typeof BrandSchema>;

export const BrandSchema = z.object({
  name: z.string().min(1, { error: "The name is required" })
});

export const brandToFormData = (brand?: Brand): BrandFormData => ({
  name: brand?.name ?? ""
});
