import MaterialIcons from "@expo/vector-icons/MaterialIcons";
import { Href, useRouter } from "expo-router";
import { Pressable, Text, View } from "react-native";
import Animated, { FadeIn } from "react-native-reanimated";

interface StatCardProps {
  title: string;
  value: string | number;
  loading?: boolean;
  trend?: string;
  href?: Href;
}

export const StatCard = ({
  title,
  value,
  loading = false,
  trend,
  href,
}: StatCardProps) => {
  const router = useRouter();

  return (
    <Animated.View
      entering={FadeIn}
      className="flex-1 bg-white rounded-2xl border border-slate-300"
    >
      <Pressable 
        disabled={!href} 
        onPress={() => href ? router.push(href) : null}
        className="p-4"
      >
        <Text className="text-gray-500 text-sm">{title}</Text>

        {loading ? (
          <View className="h-6 w-24 bg-gray-200 rounded-md mt-2 animate-pulse" />
        ) : (
          <View className="flex-row items-end justify-between">
            <Text className="text-2xl font-semibold mt-2">{value}</Text>
            { href && <MaterialIcons name="arrow-forward" size={22}/> }
          </View>
        )}

        {trend && !loading && (
          <Text className="text-xs text-green-600 mt-1">
            {trend}
          </Text>
        )}

      </Pressable>
    </Animated.View>
  );
};
