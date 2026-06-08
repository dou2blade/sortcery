import * as z from "zod";

export type LoginFormData = z.infer<typeof LoginSchema>;

export const LoginSchema = z.object({
  email: z.email().min(1, { error: "Please enter your email" }),
  password: z.string().min(1, { error: "Please enter your password" }),
  role: z.enum(["ADMIN", "MANAGER", "RETAILER", "CONSUMER"], { error: "Please select your role" })
});
