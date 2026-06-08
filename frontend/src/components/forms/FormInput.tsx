import { useLocalSearchParams } from "expo-router";
import { Controller, FieldValues, Path, useFormContext, useFormState } from "react-hook-form";
import { TextInput, TextInputProps } from "react-native";

interface FormInputProps<T extends FieldValues> extends TextInputProps {
  name: Path<T>;
  readOnly?: boolean;
}

export const FormInput = <T extends FieldValues>({ name, readOnly, ...rest }: FormInputProps<T>) => {
  const { view } = useLocalSearchParams();

  const { control } = useFormContext();
  const { errors } = useFormState({ control, name });

  const invalid = !!errors?.[name];

  return (
      <Controller 
        control={control}
        name={name}
        render={(({ field: { onChange, value } }) => (
          <TextInput 
            value={value ?? ""}
            onChangeText={onChange}
            className={`
              rounded-xl 
              border 
              px-4 
              py-3 
              text-base 
              ${!!view || readOnly ? "bg-slate-200" : "bg-white"}
              ${invalid ? "border-red-500" : "border-gray-300"}
              ${value ? "" : "text-slate-500"}
            `}
            readOnly={!!view || readOnly}
            {...rest}
          />
        ))}
      />
  );
}
