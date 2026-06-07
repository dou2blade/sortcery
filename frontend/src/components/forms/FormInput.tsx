import { Controller, FieldValues, Path, useFormContext, useFormState } from "react-hook-form";
import { TextInput, TextInputProps } from "react-native";

interface FormInputProps<T extends FieldValues> extends TextInputProps {
  name: Path<T>;
}

export const FormInput = <T extends FieldValues>({ name, ...rest }: FormInputProps<T>) => {
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
              bg-white 
              px-4 
              py-3 
              text-base 
              ${invalid ? "border-red-500" : "border-gray-300"}
              ${value ? "" : "text-slate-500"}
            `}
            {...rest}
          />
        ))}
      />
  );
}
