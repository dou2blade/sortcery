import { useEffect, useRef, useState } from "react";
import { Pressable, ScrollView, Text, TextInput, View } from "react-native";
import MaterialIcons from '@expo/vector-icons/MaterialIcons';
import { BottomSheetModal, BottomSheetView } from "@gorhom/bottom-sheet";
import { useLocalSearchParams } from "expo-router";
import { useMapSearch } from "@/features/maps/hooks";
import { useFormContext, useWatch } from "react-hook-form";

interface MapSearchProps {
  placeholder?: string;
  readOnly?: boolean;
}

export const MapSearch = ({
  placeholder,
  readOnly
}: MapSearchProps) => {
  const { view } = useLocalSearchParams();
  const [localSearch, setLocalSearch] = useState("");
  const [search, setSearch] = useState("");

  const { setValue } = useFormContext<{ 
    address: string,
    latitude: number,
    longitude: number 
  }>();

  const address = useWatch({ name: "address" });

  useEffect(() => {
    const timeout = setTimeout(() => {
      setSearch(localSearch)
    }, 500);

    return () => clearTimeout(timeout);
  }, [localSearch]);

  const { data } = useMapSearch(search);

  const sheetRef = useRef<BottomSheetModal>(null);
  return (
    <>
      <Pressable
        onPress={() => sheetRef.current?.present()}
        className={`
          rounded-xl border px-4 py-3 flex-1 flex-row flex-nowrap justify-between border-gray-300
          ${!!view || readOnly ? "bg-slate-200" : "bg-white"}
        `}
        disabled={!!view || readOnly}
      >
        <Text
          className={`flex-1 min-w-0 text-base ${address ? "text-black" : "text-slate-500"}`}
          numberOfLines={1}
          ellipsizeMode="tail"
        >
          {address || placeholder}
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
            value={localSearch}
            onChangeText={setLocalSearch}
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
              ${localSearch ? "" : "text-slate-500"}
            `}
          />
        </View>

          <ScrollView>
            {(data?.data ?? []).map((opt) => (
              <Pressable
                key={`${opt.label}-${opt.latitude}-${opt.longitude}`}
                onPress={() => {
                  setValue("address", opt.label);
                  setValue("latitude", opt.latitude);
                  setValue("longitude", opt.longitude);
                  sheetRef.current?.dismiss();
                }}
                className="py-3 border-b border-gray-200"
              >
                <Text>{opt.label}</Text>
              </Pressable>
            ))}
          </ScrollView>
        </BottomSheetView>
      </BottomSheetModal>
    </>
  );
};
