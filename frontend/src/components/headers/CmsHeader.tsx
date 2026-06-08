import { Text, View } from "react-native";
import { AddItemButton } from "../buttons";
import Animated, { EntryOrExitLayoutType, FadeIn } from "react-native-reanimated";
import { Href } from "expo-router";

interface CmsHeaderProps {
  title: string;
  subtitle: string;
  fadeIn?: EntryOrExitLayoutType;
  addHref?: Href;
}

export const CmsHeader = ({ title, subtitle, addHref, fadeIn }: CmsHeaderProps) => {
  return (
    <Animated.View entering={fadeIn ?? FadeIn}>
      <View className="w-full flex-row justify-between items-center p-2">
        <View className="flex">
          <Text className="text-3xl font-bold">{title}</Text>
          <Text className="text-xl">{subtitle}</Text>
        </View>
        {addHref && <AddItemButton label="Add User" href={addHref} />}
      </View>
      <View className="mb-3 h-px bg-slate-300" />
    </Animated.View>
  );
}
