import { View } from "react-native";
import { FormCancel } from "./FormCancel";
import { FormSubmit } from "./FormSubmit";
import { FieldValues, SubmitHandler } from "react-hook-form";

interface FormButtonsProps<T extends FieldValues> {
  onSubmit: SubmitHandler<T>;
}

export const FormButtons = <T extends FieldValues>({ onSubmit }: FormButtonsProps<T>) => {
  return (
    <View className="flex-row justify-end gap-3">
      <FormCancel />
      <FormSubmit onSubmit={onSubmit} />
    </View>
  );
}
