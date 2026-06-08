import { FieldValues, SubmitHandler, useFormContext } from "react-hook-form";
import { ActivityIndicator, Pressable, Text } from "react-native";

interface FormSubmitProps<T extends FieldValues> {
  onSubmit: SubmitHandler<T>;
  label?: string;
}

export const FormSubmit = <T extends FieldValues>({ onSubmit, label }: FormSubmitProps<T>) => {
  const { 
    handleSubmit,
    formState: { isSubmitting }
  } = useFormContext<T>();

  return (
    <Pressable 
      className="bg-green-600 rounded-lg p-3"
      onPress={handleSubmit(onSubmit)} 
      disabled={isSubmitting}
    >
      {
        isSubmitting 
          ? <ActivityIndicator color="white" />
          : <Text className="text-white text-center">{label ?? "Submit"}</Text>
      }
    </Pressable>
  );
}
