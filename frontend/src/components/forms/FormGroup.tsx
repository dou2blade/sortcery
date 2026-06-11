import { SelectOption } from "@/features/ui/types";
import { FieldValues, Path } from "react-hook-form";
import { TextInputProps, View } from "react-native";
import { FormInput } from "./FormInput";
import { FormSelect } from "./FormSelect";
import { FormLabel } from "./FormLabel";
import { FormFeedback } from "./FormFeedback";
import { FormList } from "./FormList";
import { FormListReadOnly } from "./FormListReadOnly";
import { Href } from "expo-router";

interface BaseFormGroupProps {
  label?: string;
  flex?: number;
}

interface TextFieldProps<T extends FieldValues> extends BaseFormGroupProps, TextInputProps {
  type?: "text";
  name: Path<T>;
  optional?: boolean;
  readOnly?: boolean;
}

interface SelectFieldProps<T extends FieldValues> extends BaseFormGroupProps {
  type: "select";
  name: Path<T>;
  options: SelectOption[];
  placeholder?: string;
  optional?: boolean;
  readOnly?: boolean;
}

interface ListFieldProps<T extends FieldValues> extends BaseFormGroupProps {
  type: "list";
  name: Path<T>;
  options: SelectOption[];
  optional?: boolean;
  readOnly?: boolean;
}

interface ListReadOnlyFieldProps extends BaseFormGroupProps {
  type: "list-readonly";
  label: string;
  values: SelectOption[];
  href: Href;
  name?: never;
  optional?: never;
  readOnly?: never;

}

type FormGroupProps<T extends FieldValues> = 
  | TextFieldProps<T>
  | SelectFieldProps<T>
  | ListFieldProps<T>
  | ListReadOnlyFieldProps

const FormGroup = <T extends FieldValues>(props: FormGroupProps<T>) => {
  const { flex, label, type, ...rest } = props;

  const renderLabel = label ?? String(props.name).split('')
    .map((c, idx) => { 
      if (idx === 0) return c.toUpperCase();
      const isUpperCase = c === c.toUpperCase() 
        && c !== c.toLowerCase();
      return isUpperCase ? ` ${c}` : c;
    })
    .join('');

  let field;
  switch (type) {
    case "select": 
      field = <FormSelect 
        name={props.name} 
        options={props.options}
        placeholder={props.placeholder}
        {...rest} 
      />
      break;
    case "list":
      field = <FormList 
        name={props.name}
        options={props.options}
        optional={props.optional}
        label={renderLabel}
      />
      break;
    case "list-readonly":
      field = <FormListReadOnly 
        label={renderLabel}
        values={props.values}
        href={props.href}
      />
      break;
    default:
      field = <FormInput name={props.name} {...rest} />
      break;
  }

  if (type === "list" || type === "list-readonly") return field;

  return (
    <View style={{ flex: flex ?? 1 }}>
      <FormLabel optional={props.optional}>{ renderLabel }</FormLabel> 
        {field} 
      <FormFeedback name={props.name} />
    </View>
  );
}

export default FormGroup;
