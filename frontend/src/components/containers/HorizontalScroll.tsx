import { ScrollView, View } from "react-native";
import Animated, { FadeIn } from "react-native-reanimated";

export const HorizontalScroll = ({ children }: {
  children: React.ReactNode;
}) => {

  return (
    <Animated.View entering={FadeIn}>
      <ScrollView
        horizontal
        showsHorizontalScrollIndicator={false}
        contentContainerStyle={{
          gap: 12,
          paddingHorizontal: 12,
        }}
      >
        {children}
      </ScrollView>
    </Animated.View>
  );
}
