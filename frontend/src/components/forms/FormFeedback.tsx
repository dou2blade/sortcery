import { FieldValues, Path, useFormContext, useFormState } from "react-hook-form";
import { Text } from "react-native";

const FormFeedback = <T extends FieldValues>({ name }: { name: Path<T> }) => {
  const { control } = useFormContext();
  const { errors } = useFormState({ control, name });
  const message = errors?.[name]?.message;

  if (!message) return null;

  return message 
    ? <Text className="text-red-500 ms-2 mt-1">{String(message)}</Text>
    : null;
}

export default FormFeedback;
