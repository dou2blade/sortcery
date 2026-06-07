import { useRef, useState } from "react";
import { Controller, FieldValues, Path, useFormContext, useFormState } from "react-hook-form";
import { Pressable, Text } from "react-native";
import MaterialIcons from '@expo/vector-icons/MaterialIcons';
import { BottomSheetModal, BottomSheetView } from "@gorhom/bottom-sheet";
import { SelectOption } from "@/features/ui/types";

interface FormSelectProps<T extends FieldValues> {
  name: Path<T>;
  options: SelectOption[];
  placeholder?: string;
}

export const FormSelect = <T extends FieldValues>({
  name,
  options,
  placeholder
}: FormSelectProps<T>) => {
  const [label, setLabel] = useState<string | null>(null);
  const { control } = useFormContext<T>();
  const { errors } = useFormState({ control, name });

  const sheetRef = useRef<BottomSheetModal>(null);

  const invalid = !!errors?.[name];

  return (
    <Controller
      control={control}
      name={name}
      render={({ field: { onChange, value } }) => (
        <>
          <Pressable
            onPress={() => sheetRef.current?.present()}
            className={`
              rounded-xl border bg-white px-4 py-3 flex-1 flex-row justify-between
              ${invalid ? "border-red-500" : "border-gray-300"}
            `}
          >
            <Text className={`text-base ${value ? "text-black" : "text-slate-500"}`}>
              {label ?? placeholder ?? ""}
            </Text>

            <MaterialIcons name="expand-more" size={16} color="gray" />
          </Pressable>

          <BottomSheetModal
            ref={sheetRef}
            snapPoints={["25%", "50%", "90%"]}
            index={0}
          >
            <BottomSheetView className="p-4">
              {options.map((opt) => (
                <Pressable
                  key={opt.value}
                  onPress={() => {
                    onChange(opt.value);
                    setLabel(opt.label);
                    sheetRef.current?.dismiss();
                  }}
                  className="py-3 border-b border-gray-200"
                >
                  <Text>{opt.label}</Text>
                </Pressable>
              ))}
            </BottomSheetView>
          </BottomSheetModal>
        </>
      )}
    />
  );
};
