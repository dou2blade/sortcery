import { useLocalSearchParams } from "expo-router";
import { FieldValues, SubmitHandler, useFormContext } from "react-hook-form";
import { ActivityIndicator, Pressable, Text } from "react-native";

interface FormSubmitProps<T extends FieldValues> {
  onSubmit: SubmitHandler<T>;
  label?: string;
  readOnly?: boolean;
}

export const FormSubmit = <T extends FieldValues>({ onSubmit, label, readOnly }: FormSubmitProps<T>) => {
  const { view } = useLocalSearchParams();

  const { 
    handleSubmit,
    formState: { isSubmitting }
  } = useFormContext<T>();

  return (
    <Pressable 
      className={`
        min-w-[70px]
        items-center
        rounded-lg 
        p-3
        ${!!view || readOnly || isSubmitting ? "bg-green-800/70" : "bg-green-800" } 
      `}
      onPress={handleSubmit(onSubmit)} 
      disabled={!!view || readOnly || isSubmitting}
    >
      {
        isSubmitting 
          ? <ActivityIndicator color="white" />
          : <Text className="text-white text-center">{label ?? "Submit"}</Text>
      }
    </Pressable>
  );
}
