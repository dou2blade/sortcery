import { Text, View } from "react-native";

export const CmsCardsContainer = ({ children, title }: { 
  children: React.ReactNode, 
  title?: string
}) => {
  if (title) {
    return (
      <View>
        <Text className="text-lg font-bold m-2">{title}</Text>
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
