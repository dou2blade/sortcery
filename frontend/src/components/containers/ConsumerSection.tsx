import MaterialIcons from "@expo/vector-icons/MaterialIcons";
import { Pressable, Text, View } from "react-native";
import Animated, { FadeIn } from "react-native-reanimated";

interface ConsumerSectionProps {
  title: string;
  onSeeAll?: () => void;
  children: React.ReactNode;
}

export const ConsumerSection = ({
  title,
  onSeeAll,
  children,
}: ConsumerSectionProps) => (
  <Animated.View entering={FadeIn}>
    <View className="flex-row justify-between items-center m-3">
      <Text className="text-lg font-bold">
        {title}
      </Text>

      {onSeeAll && (
        <Pressable onPress={onSeeAll}>
          <View className="flex-row items-center">
            <Text className="font-bold me-1">
              See All
            </Text>
            <MaterialIcons name="arrow-forward" size={18}/>
          </View>
        </Pressable>
      )}
    </View>

    {children}
  </Animated.View>
);
