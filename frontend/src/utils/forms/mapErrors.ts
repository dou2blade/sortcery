import { FieldValues, Path, UseFormSetError } from "react-hook-form";

export const mapErrors = <T extends FieldValues>(setError: UseFormSetError<T>, errors: Record<string, any>) => {
  Object.entries(errors).forEach(([k, v]) => {
    setError(k as Path<T>, { message: v }, { shouldFocus: false });
  });
}
