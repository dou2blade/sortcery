import { View } from "react-native";
import { ScrollView } from "react-native-gesture-handler";

export const CmsContainer = ({ children }: { children: React.ReactNode }) => {
  return (
    <ScrollView className="flex-1">
      <View className="m-3 gap-3">
        {children}
      </View>
    </ScrollView>
  );
}
