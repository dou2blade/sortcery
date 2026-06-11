import { View } from "react-native";

export const CmsFiltersContainer = ({ children }: { children: React.ReactNode }) => {
  return (
    <View className="flex-row flex-wrap gap-3 w-full">
      {children}
    </View>
  );
}
