import { SelectOption } from "@/features/ui/types";
import MaterialIcons from "@expo/vector-icons/MaterialIcons";
import { BottomSheetModal, BottomSheetView } from "@gorhom/bottom-sheet";
import { useLocalSearchParams, useRouter } from "expo-router";
import { useRef } from "react";
import { Pressable, PressableProps, Text } from "react-native";
import Animated, { FadeIn } from "react-native-reanimated";

interface SelectFilterProps extends PressableProps {
  name: string;
  options: SelectOption[];
  placeholder?: string;
}

export const SelectFilter = ({ name, options, placeholder, ...rest }: SelectFilterProps) => {
  const router = useRouter();
  const { [name]: value } = useLocalSearchParams();

  const sheetRef = useRef<BottomSheetModal>(null);

  const label = options.find((opt) => opt.value == value)?.label;

  const { className, ...props } = rest;

  return (
    <Animated.View entering={FadeIn}>
      <Pressable
        onPress={() => sheetRef.current?.present()}
        className={`
          w-full rounded-xl border bg-white px-4 py-3 flex-1 flex-row justify-between border-gray-300 
          ${className}
        `}
        {...props}
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

          <Pressable
            onPress={() => {
              router.setParams({ [name]: undefined });
              sheetRef.current?.dismiss();
            }}
            className="py-3 border-b border-gray-200"
          >
            <Text>Clear</Text>
          </Pressable>

          {options.map((opt) => (
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
          ))}
        </BottomSheetView>
      </BottomSheetModal>
    </Animated.View>
  );
}
