import { Href, useRouter } from "expo-router";
import { Pressable, Text, View } from "react-native";
import MaterialIcons from "@expo/vector-icons/MaterialIcons";

interface StoreCardProps {
  name: string;
  address?: string;
  storeName?: string;
  distance?: number;
  href?: Href;
  loading?: boolean;
}

export const StoreCard = ({
  name,
  address,
  storeName,
  distance,
  href,
  loading = false,
}: StoreCardProps) => {
  const router = useRouter();

  if (loading) {
    return (
      <View className="w-64 rounded-xl border border-slate-300 bg-white p-4">
        <View className="h-5 w-3/4 rounded bg-slate-200" />

        <View className="mt-2 h-4 w-1/2 rounded bg-slate-200" />

        <View className="mt-4 h-4 w-full rounded bg-slate-200" />

        <View className="mt-2 h-4 w-4/5 rounded bg-slate-200" />
      </View>
    );
  }

  return (
    <Pressable
      onPress={() => href && router.push(href)}
      className="w-64 rounded-xl border border-slate-300 bg-white p-4"
    >
      <View className="flex-row items-start justify-between">
        <View className="flex-1">
          <Text
            numberOfLines={1}
            className="text-lg font-semibold"
          >
            {storeName}
          </Text>

          {storeName && (
            <Text
              numberOfLines={1}
              className="mt-1 text-sm text-slate-500"
            >
              {name} Branch
            </Text>
          )}
        </View>

        {href && (
          <MaterialIcons
            name="arrow-forward"
            size={20}
          />
        )}
      </View>

      {distance != null && (
        <View className="mt-3 flex-row items-center gap-1">
          <MaterialIcons
            name="location-on"
            size={16}
          />
          <Text className="text-sm font-medium">
            {distance.toFixed(2)} km away
          </Text>
        </View>
      )}

      {address && (
        <Text
          numberOfLines={2}
          className="mt-2 text-sm text-slate-600"
        >
          {address}
        </Text>
      )}
    </Pressable>
  );
};
