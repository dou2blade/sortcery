import { SelectOption } from "@/features/ui/types";
import { FieldValues, Path } from "react-hook-form";
import { TextInputProps, View } from "react-native";
import { FormInput } from "./FormInput";
import { FormSelect } from "./FormSelect";
import { FormLabel } from "./FormLabel";
import { FormFeedback } from "./FormFeedback";
import { FormList } from "./FormList";

interface BaseFormGroupProps<T extends FieldValues> {
  name: Path<T>;
  label?: string;
  flex?: number;
  optional?: boolean;
  readOnly?: boolean;
}

interface TextFieldProps<T extends FieldValues> extends BaseFormGroupProps<T>, TextInputProps {
  type?: "text";
}

interface SelectFieldProps<T extends FieldValues> extends BaseFormGroupProps<T> {
  type: "select";
  options: SelectOption[];
  placeholder?: string;
}

interface ListFieldProps<T extends FieldValues> extends BaseFormGroupProps<T> {
  type: "list";
  options: SelectOption[];
}

type FormGroupProps<T extends FieldValues> = 
  | TextFieldProps<T>
  | SelectFieldProps<T>
  | ListFieldProps<T>

const FormGroup = <T extends FieldValues>(props: FormGroupProps<T>) => {
  const { flex, name, label, type, optional, ...rest } = props;

  const renderLabel = label ?? String(name).split('')
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
    case "list":
      field = <FormList 
        name={name}
        options={props.options}
        optional={optional}
        label={renderLabel}
      />
    default:
      break;
  }

  if (type === "list") return field;

  return (
    <View style={{ flex: flex ?? 1 }}>
      <FormLabel optional={optional}>{ renderLabel }</FormLabel> 
        {field} 
      <FormFeedback name={name} />
    </View>
  );
}

export default FormGroup;
