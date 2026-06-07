import { SelectOption } from "@/features/ui/types";
import { FieldValues, Path } from "react-hook-form";
import { TextInputProps, View } from "react-native";
import { FormFeedback, FormInput, FormLabel, FormSelect } from "@/components/forms";

interface BaseFormGroupProps<T extends FieldValues> {
  name: Path<T>;
  label?: string;
}

interface TextFieldProps<T extends FieldValues> extends BaseFormGroupProps<T>, TextInputProps {
  type?: "text";
}

interface SelectFieldProps<T extends FieldValues> extends BaseFormGroupProps<T> {
  type: "select";
  options: SelectOption[];
  placeholder?: string;
}

type FormGroupProps<T extends FieldValues> = 
  | TextFieldProps<T>
  | SelectFieldProps<T>

const FormGroup = <T extends FieldValues>(props: FormGroupProps<T>) => {
  const { name, label, type, ...rest } = props;

  const renderLabel = label ?? String(name).toLowerCase()
    .split('')
    .map((c, idx) => { 
      if (idx === 0) return c.toUpperCase();
      const isUpperCase = c === c.toUpperCase() 
        && c !== c.toLowerCase();
      return isUpperCase ? ` ${c}` : c;
    })
    .join('');

  let field = <FormInput name={name} {...rest} />;
  switch (type) {
    case "select": 
      field = <FormSelect 
        name={name} 
        options={props.options}
        placeholder={props.placeholder}
        {...rest} 
      />
      break;
    default:
      break;
  }

  return (
    <View className="w-100">
      <FormLabel>{ renderLabel }</FormLabel>
      {field}
      <FormFeedback name={name} />
    </View>
  );
}

export default FormGroup;
