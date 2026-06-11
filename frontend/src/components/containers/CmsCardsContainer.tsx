import { View } from "react-native";
import Animated, { FadeIn } from "react-native-reanimated";

export const CmsCardsContainer = ({ children, title }: { 
  children: React.ReactNode, 
  title?: string
}) => {
  if (title) {
    return (
      <View>
        <Animated.Text className="text-lg font-bold m-2" entering={FadeIn}>
          {title}
        </Animated.Text>
        <View className="flex-row gap-3">
          {children}
        </View>
      </View>
    )
  } else {
    return (
      <View className="flex-row gap-3">
        {children}
      </View>
    ); 
  }
}
