import { ApiResponse } from "@/utils/api";
import { mapErrors } from "@/utils/forms";
import toast from "@/utils/toast";
import { UseMutationResult } from "@tanstack/react-query";
import { useRouter } from "expo-router";
import { FieldValues, SubmitHandler, UseFormReturn } from "react-hook-form";

interface UseSubmitHandlerProps<T extends FieldValues> {
  form: UseFormReturn<T>;
  id?: number;
  entity: string;
  create: UseMutationResult<ApiResponse<unknown>, Error, T>;
  update: UseMutationResult<ApiResponse<unknown>, Error, { id: number; payload: T }>;
}

export const useSubmitHandler = <T extends FieldValues>({
  form,
  id,
  entity,
  create,
  update
}: UseSubmitHandlerProps<T>): SubmitHandler<T> => {
  const router = useRouter();

  return async (payload) => {
    try {
      const { errors } = id
        ? await update.mutateAsync({ id, payload })
        : await create.mutateAsync(payload)

      if (errors) {
        mapErrors(form.setError, errors);
        return;
      }

      toast.cmsSuccess(!id, entity);
      router.dismiss();
    } catch {
      toast.cmsError(!id, entity);
    }
  };
}
