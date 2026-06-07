import { Text, View } from "react-native";
import Animated, { EntryOrExitLayoutType, FadeIn } from "react-native-reanimated";

interface StatCardProps {
  title: string;
  value: string | number;
  loading?: boolean;
  trend?: string;
  fadeIn?: EntryOrExitLayoutType;
}

export const StatCard = ({
  title,
  value,
  loading = false,
  trend,
  fadeIn
}: StatCardProps) => {
  return (
    <Animated.View
      entering={fadeIn ?? FadeIn}
      className="flex-1 bg-white rounded-2xl p-4 border border-slate-300"
    >
      <Text className="text-gray-500 text-sm">{title}</Text>

      {loading ? (
        <View className="h-6 w-24 bg-gray-200 rounded-md mt-2 animate-pulse" />
      ) : (
        <Text className="text-2xl font-semibold mt-2">{value}</Text>
      )}

      {trend && !loading && (
        <Text className="text-xs text-green-600 mt-1">
          {trend}
        </Text>
      )}
    </Animated.View>
  );
};
