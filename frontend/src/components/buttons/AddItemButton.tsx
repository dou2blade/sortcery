import MaterialIcons from "@expo/vector-icons/MaterialIcons"
import { Href, useRouter } from "expo-router";
import { Pressable, Text } from "react-native"
import Animated, { EntryOrExitLayoutType, FadeIn } from "react-native-reanimated"

interface AddItemButtonProps {
  label: string;
  href: Href;
  fadeIn?: EntryOrExitLayoutType;
}

export const AddItemButton = ({ label, href, fadeIn }: AddItemButtonProps) => {
  const router = useRouter();
  
  return (
    <Animated.View entering={fadeIn ?? FadeIn}>
      <Pressable
        onPress={() => router.push(href)}
        className="rounded-lg bg-green-600 px-3 py-2 flex-row items-center gap-1"
      >
        <MaterialIcons name="add" size={18} color="white" />
        <Text className="text-base text-white">
          {label}
        </Text>
      </Pressable>
    </Animated.View>
  )
}
