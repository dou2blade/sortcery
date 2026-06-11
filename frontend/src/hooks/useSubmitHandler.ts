import { ApiResponse } from "@/utils/api";
import { mapErrors } from "@/utils/forms";
import toast from "@/utils/toast";
import { UseMutationResult } from "@tanstack/react-query";
import { useRouter } from "expo-router";
import { FieldValues, SubmitHandler, UseFormReturn } from "react-hook-form";

type CreateMutation<T extends FieldValues> =
  UseMutationResult<ApiResponse<unknown>, Error, T>;

type UpdateMutation<T extends FieldValues> =
  UseMutationResult<ApiResponse<unknown>, Error, { id: number; payload: T }>;

interface UseSubmitHandlerProps<T extends FieldValues> {
  form: UseFormReturn<T>;
  entity: string;
  id?: number;

  create?: CreateMutation<T>;
  update?: UpdateMutation<T>;
}

export const useSubmitHandler = <T extends FieldValues>({
  form,
  entity,
  id,
  create,
  update,
}: UseSubmitHandlerProps<T>): SubmitHandler<T> => {
  const router = useRouter();

  return async (payload) => {
    try {
      let response: ApiResponse<unknown>;

      if (id !== undefined) {
        if (!update) {
          throw new Error("Update mutation not provided");
        }

        response = await update.mutateAsync({
          id,
          payload,
        });
      } else {
        if (!create) {
          throw new Error("Create mutation not provided");
        }

        response = await create.mutateAsync(payload);
      }

      if (response.errors) {
        mapErrors(form.setError, response.errors);
        return;
      }

      toast.cmsSuccess(id === undefined, entity);
      router.dismiss();
    } catch {
      toast.cmsError(id === undefined, entity);
    }
  };
};
