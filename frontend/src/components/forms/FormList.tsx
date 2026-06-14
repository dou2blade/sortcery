import { useRef, useState } from "react";
import { Controller, FieldValues, Path, useFormContext, useFormState, useWatch } from "react-hook-form";
import { Pressable, ScrollView, Text, TextInput, View } from "react-native";
import MaterialIcons from '@expo/vector-icons/MaterialIcons';
import { BottomSheetModal, BottomSheetView } from "@gorhom/bottom-sheet";
import { SelectOption } from "@/features/ui/types";
import { Href, useLocalSearchParams, useRouter } from "expo-router";
import { FormLabel } from "./FormLabel";
import { FormFeedback } from "./FormFeedback";
import { useAuthStore } from "@/features/auth/stores";

interface FormListProps<T extends FieldValues> {
  name: Path<T>;
  label: string;
  options: SelectOption[];
  optional?: boolean;
  readOnly?: boolean;
  href?: Href;
}

export const FormList = <T extends FieldValues>({
  name,
  label,
  options,
  optional,
  readOnly,
  href
}: FormListProps<T>) => {
  const router = useRouter();

  const { view } = useLocalSearchParams();
  const [search, setSearch] = useState("");

  const { control } = useFormContext<T>();
  const { errors } = useFormState({ control, name });

  const values = useWatch({ name }) as unknown[];

  const sheetRef = useRef<BottomSheetModal>(null);

  const { user } = useAuthStore();

  const invalid = !!errors?.[name];

  return (
    <Controller
      control={control}
      name={name}
      render={({ field: { onChange, value } }) => (
        <>
          <View>
            <View
              className={`
                flex-row
                rounded-t-xl
                border
                bg-slate-200
                p-2
                justify-between
                items-center
                ${invalid ? "border-red-500" : "border-slate-300"}
              `}
            >
              <View className="flex-row gap-2">
                <FormLabel optional={optional}>{label}</FormLabel>
                <FormFeedback name={name} />
              </View>
              <Pressable
                onPress={() => sheetRef.current?.present()}
                className={`
                  items-center
                  rounded
                  rounded-tr-xl
                  p-2
                  ${!!view || readOnly ? "bg-green-800/70" : "bg-green-800" } 
                `}
                disabled={!!view || readOnly}
              >
                <MaterialIcons name="add" size={14} color="white" />
              </Pressable>
            </View>
            <View className="items-stretch p-3 w-full rounded-b-lg border-b border-x border-gray-300">
              {!values.length && <Text className="text-gray-700 text-center p-3">No items</Text>}
              {values.map((v, idx) => (
                <View
                  key={idx}
                  className="
                    w-full
                    flex-row
                    bg-white
                    px-2
                  "
                >
                  <View style={{ flex: 1, padding: 12, minWidth: 0 }}>
                    <Text numberOfLines={1}>
                      {idx + 1}
                    </Text>
                  </View>
                  <View style={{ flex: 14, padding: 12, minWidth: 0 }}>
                    <Text numberOfLines={1}>
                      {options.find((opt) => opt.value === v)?.label}
                    </Text>
                  </View>
                  <View style={{ flex: 1, padding: 12, minWidth: 0 }} className="items-center justify-end flex-row gap-2">
                    {(!!view || readOnly) && href &&
                      <Pressable onPress={() => router.push(`${href}/${v}`)}>
                        <MaterialIcons name="arrow-forward" className="text-black" size={14}/>
                      </Pressable>
                    }

                    {!view && !readOnly && user?.id !== v &&
                      <Pressable
                        onPress={() => {
                          const newValue = [...value];
                          newValue.splice(idx, 1);
                          onChange(newValue);
                        }}
                      >
                        <MaterialIcons name="highlight-remove" className="text-red-500" size={14}/>
                      </Pressable>
                    }
                  </View>
                </View>
              )) }
            </View>
          </View>

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
              {options.filter((opt) => (!search 
                || opt.label.toLowerCase().includes(search.toLowerCase()))
                && !(value as unknown[]).includes(opt.value)
              ).map((opt) => (
                  <Pressable
                    key={opt.value}
                    onPress={() => {
                      onChange([...value, opt.value]);
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
