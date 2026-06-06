import { FieldValues, SubmitErrorHandler, SubmitHandler, useFormContext } from "react-hook-form";
import { ActivityIndicator, Pressable, Text } from "react-native";

interface FormSubmitProps<T extends FieldValues> {
  onSubmit: SubmitHandler<T>;
  label?: string;
}
const FormSubmit = <T extends FieldValues>({ onSubmit, label }: FormSubmitProps<T>) => {
  const { 
    handleSubmit,
    formState: { isSubmitting }
  } = useFormContext<T>();

  const onError: SubmitErrorHandler<T> = (error) => {
    console.log(error);
  }

  return (
    <Pressable 
      className="bg-green-600 rounded-lg p-3 w-100"
      onPress={handleSubmit(onSubmit, onError)} 
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

export default FormSubmit;
