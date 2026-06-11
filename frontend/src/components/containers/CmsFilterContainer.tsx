import { View } from "react-native";

export const CmsFilterContainer = ({ children, flex }: {
  children: React.ReactNode;
  flex?: number;
}) => {
  return (
    <View className={`w-full ${flex ? `md:flex-[${flex}]` : "md:flex-1"}`}>
      {children}
    </View>
  );
}
