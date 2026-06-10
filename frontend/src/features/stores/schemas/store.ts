import * as z from "zod";
import { Store } from "../types";

export type StoreFormData = z.infer<typeof StoreSchema>;

export const StoreSchema = z.object({
  name: z.string().min(1, { error: "The name is required" }),
})

export const storeToFormData = (store?: Store): StoreFormData => ({
  name: store?.name ?? "",
});
