import * as z from "zod";
import { User } from "../types";

export type UserFormData = z.infer<typeof UserSchema>;

export const UserSchema = z.object({
  isCreate: z.boolean(),
  firstName: z.string().min(1, { error: "The first name is required" }),
  middleName: z.string().optional(),
  lastName: z.string().min(1, { error: "The last name is required" }),
  email: z.email().min(1, { error: "The email is required" }),
  password: z.string().optional(),
  role: z.enum(["ADMIN", "MANAGER", "RETAILER", "CONSUMER"], { error: "The role is required" })
    .optional()
    .refine((v) => v !== undefined, { error: "The role is required" })
}).superRefine(({ isCreate, password }, ctx) => {
  if (isCreate && !password?.length) {
    ctx.addIssue({
      code: "custom",
      message: "The password is required",
      path: ["password"]
    });
  }

  if (password && password?.length > 0 && password?.length < 8) {
    ctx.addIssue({
      code: "custom",
      message: "The Password must be atleast 8 characters",
      path: ["password"]
    });
  }
});

export const userToFormData = (user?: User): UserFormData => ({
  isCreate: !user,
  firstName: user?.firstName ?? "",
  middleName: user?.middleName ?? "",
  lastName: user?.lastName ?? "",
  email: user?.email ?? "",
  password: "",
  role: user?.role,
});
