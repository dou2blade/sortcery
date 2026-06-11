import { View } from "react-native";

export const CmsCardsContainer = ({ children }: { children: React.ReactNode }) => {
  return (
    <View className="flex-row gap-3">
      {children}
    </View>
  ); 
}
