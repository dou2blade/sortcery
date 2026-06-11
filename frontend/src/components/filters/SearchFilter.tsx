import { useLocalSearchParams, useRouter } from "expo-router";
import { useEffect, useState } from "react";
import { TextInput, View } from "react-native";
import MaterialIcons from "@expo/vector-icons/MaterialIcons";
import Animated, { FadeIn } from "react-native-reanimated";

interface SearchFilterProps {
  name: string;
  placeholder?: string;
}

export const SearchFilter = ({ name, placeholder }: SearchFilterProps) => {
  const router = useRouter();
  const { [name]: queryValue = "" } = useLocalSearchParams();

  const [value, setValue] = useState(
    Array.isArray(queryValue) ? queryValue[0] : queryValue
  );

  useEffect(() => {
    const timeout = setTimeout(() => {
      router.setParams({ [name]: value || undefined });
    }, 500);

    return () => clearTimeout(timeout);
  }, [value]);

  return (
    <Animated.View className="relative w-full" entering={FadeIn}>
      <View className="absolute left-3 top-3 z-10">
        <MaterialIcons name="search" size={22} color="gray" />
      </View>

      <TextInput
        value={value}
        onChangeText={setValue}
        placeholder={placeholder}
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
          ${value ? "" : "text-slate-500"}
        `}
      />
    </Animated.View>
  );
};
