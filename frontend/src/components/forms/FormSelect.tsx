import { useRef, useState } from "react";
import { Controller, FieldValues, Path, useFormContext, useFormState } from "react-hook-form";
import { Pressable, ScrollView, Text, TextInput, View } from "react-native";
import MaterialIcons from '@expo/vector-icons/MaterialIcons';
import { BottomSheetModal, BottomSheetView } from "@gorhom/bottom-sheet";
import { SelectOption } from "@/features/ui/types";
import { useLocalSearchParams } from "expo-router";

interface FormSelectProps<T extends FieldValues> {
  name: Path<T>;
  options: SelectOption[];
  placeholder?: string;
  readOnly?: boolean;
}

export const FormSelect = <T extends FieldValues>({
  name,
  options,
  placeholder,
  readOnly
}: FormSelectProps<T>) => {
  const { view } = useLocalSearchParams();
  const [search, setSearch] = useState("");

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
              rounded-xl border px-4 py-3 flex-1 flex-row justify-between
              ${!!view || readOnly ? "bg-slate-200" : "bg-white"}
              ${invalid ? "border-red-500" : "border-gray-300"}
            `}
            disabled={!!view || readOnly}
          >
            <Text className={`text-base ${value ? "text-black" : "text-slate-500"}`}>
              {options.find((opt) => opt.value === value)?.label ?? placeholder ?? ""}
            </Text>

            <MaterialIcons name="expand-more" size={16} color="gray" />
          </Pressable>

          <BottomSheetModal
            ref={sheetRef}
            snapPoints={["25%", "50%", "90%"]}
            index={0}
          >
            <BottomSheetView className="p-4">
            <View className="relative w-full mb-3">
              <View className="absolute left-3 top-3 z-10">
                <MaterialIcons name="search" size={22} color="gray" />
              </View>

              <TextInput
                value={search}
                onChangeText={setSearch}
                placeholder="Search..."
                className={`
                  w-full
                  rounded-xl
                  border
                  bg-white
                  pl-10
                  pr-4
                  py-3
                  text-base
                  border-gray-300
                  ${search ? "" : "text-slate-500"}
                `}
              />
            </View>

            <ScrollView>
              {options.filter((opt) => !search 
                || opt.label.toLowerCase().includes(search.toLowerCase()) 
              ).map((opt) => (
                  <Pressable
                    key={opt.value}
                    onPress={() => {
                      onChange(opt.value);
                      sheetRef.current?.dismiss();
                    }}
                    className="py-3 border-b border-gray-200"
                  >
                    <Text>{opt.label}</Text>
                  </Pressable>
                ))
              }
            </ScrollView>
            </BottomSheetView>
          </BottomSheetModal>
        </>
      )}
    />
  );
};
