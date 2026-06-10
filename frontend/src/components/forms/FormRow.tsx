import { View } from "react-native";

export const FormRow = ({ children }: { children: React.ReactNode }) => {
  return (
    <View className="flex-row flex-wrap gap-3">
      {children}
    </View>
  );
}
