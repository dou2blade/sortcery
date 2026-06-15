import { Dispatch, SetStateAction, useMemo, useRef, useState } from "react";
import { Pressable, ScrollView, Text, TextInput, View } from "react-native";
import MaterialIcons from "@expo/vector-icons/MaterialIcons";
import BottomSheet, { BottomSheetFlatList, BottomSheetModal, BottomSheetScrollView, BottomSheetView } from "@gorhom/bottom-sheet";
import { SelectOption } from "@/features/ui/types";
import { useLocalSearchParams, useRouter } from "expo-router";

interface FilterChipProps {
  name: string;
  options: SelectOption[];
  label: string;
}

export const FilterChip = ({
  name,
  options,
  label
}: FilterChipProps) => {
  const router = useRouter();
  const { [name]: value } = useLocalSearchParams();
  const [search, setSearch] = useState("");

  const sheetRef = useRef<BottomSheetModal>(null);

  const selectedLabel = options.find((opt) => opt.value == value)?.label;

  return (
    <>
      <Pressable
        onPress={() => sheetRef.current?.present()}
        className="flex-1 flex-row items-center justify-between gap-2 rounded-xl border border-slate-300 bg-white px-4 py-3"
      >
        <Text
          numberOfLines={1}
          className={value ? "font-medium" : "text-slate-500"}
        >
          {selectedLabel ?? label}
        </Text>

        <MaterialIcons name="keyboard-arrow-down" size={20} />
      </Pressable>

      <BottomSheetModal
        ref={sheetRef}
        snapPoints={["25%", "50%", "90%"]}
        index={0}
      >
        <BottomSheetScrollView className="p-4">
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
            <Pressable
              onPress={() => {
                router.setParams({ [name]: undefined });
                sheetRef.current?.dismiss();
              }}
              className="py-3 border-b border-gray-200"
            >
              <Text>Clear</Text>
            </Pressable>

            {options.filter((opt) => !search 
              || opt.label.toLowerCase().includes(search.toLowerCase()) 
            ).map((opt) => (
                <Pressable
                  key={opt.value}
                  onPress={() => {
                    router.setParams({ [name]: opt.value })
                    sheetRef.current?.dismiss();
                  }}
                  className="py-3 border-b border-gray-200"
                >
                  <Text>{opt.label}</Text>
                </Pressable>
              ))
            }
          </ScrollView>
        </BottomSheetScrollView>
      </BottomSheetModal>
    </>
  );
};
