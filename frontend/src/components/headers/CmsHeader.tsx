import { Text, View } from "react-native";
import { AddItemButton } from "../buttons";
import Animated, { FadeIn } from "react-native-reanimated";
import { Href } from "expo-router";

interface CmsHeaderPropsBase {
  title: string;
  subtitle: string;
}

interface CmsHeaderPropsAdd extends CmsHeaderPropsBase {
  addLabel: string;
  addHref: Href;
}

interface CmsHeaderPropsNoAdd extends CmsHeaderPropsBase {
  addLabel?: never;
  addHref?: never;
}

type CmsHeaderProps = 
  | CmsHeaderPropsAdd
  | CmsHeaderPropsNoAdd;

export const CmsHeader = ({ title, subtitle, addHref, addLabel }: CmsHeaderProps) => {
  return (
    <Animated.View entering={FadeIn}>
      <View className="w-full flex-row justify-between items-center p-2">
        <View className="flex">
          <Text className="text-3xl font-bold">{title}</Text>
          <Text className="text-xl">{subtitle}</Text>
        </View>
        {addHref && addLabel && <AddItemButton label={addLabel} href={addHref} />}
      </View>
      <View className="mb-3 h-px bg-slate-300" />
    </Animated.View>
  );
}
