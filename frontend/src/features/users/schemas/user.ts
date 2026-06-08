import * as z from "zod";

export type UserFormData = z.infer<typeof UserSchema>;

export const UserSchema = z.object({
  firstName: z.string().min(1, { error: "The first name is required" }),
  middleName: z.string(),
  lastName: z.string().min(1, { error: "The last name is required" }),
  email: z.email().min(1, { error: "The email is required" }),
  password: z.string().min(1, { error: "The password is required" }),
  role: z.enum(["ADMIN", "MANAGER", "RETAILER", "CONSUMER"], { error: "The role is required" })
    .optional()
    .refine((v) => v !== undefined, { error: "The role is required" })
});

export const UserDefaults: UserFormData = {
  firstName: "",
  middleName: "",
  lastName: "",
  email: "",
  password: "",
  role: undefined,
};
