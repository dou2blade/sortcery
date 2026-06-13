import { Pressable, Text, View, Image } from "react-native";
import { Href, useRouter } from "expo-router";

interface ProductCardProps {
  name: string;
  brand?: string;
  imageUrl?: string;
  price?: number;
  href?: Href;
  sales?: number;
  loading?: boolean;
}

export const ProductCard = ({
  name,
  brand,
  imageUrl,
  price,
  href,
  sales,
  loading = false,
}: ProductCardProps) => {
  const router = useRouter();

  if (loading) {
    return (
      <View className="w-40 h-64 bg-white rounded-xl border border-slate-300 p-3">
        <View className="h-32 rounded-lg bg-slate-200" />

        <View className="mt-3 h-4 w-3/4 rounded bg-slate-200" />
        <View className="mt-2 h-3 w-1/2 rounded bg-slate-200" />

        <View className="mt-auto">
          <View className="h-4 w-1/3 rounded bg-slate-200" />
          <View className="mt-2 h-3 w-1/4 rounded bg-slate-200" />
        </View>
      </View>
    );
  }

  return (
    <Pressable
      onPress={() => href && router.push(href)}
      className="w-40 h-64 overflow-hidden rounded-xl border border-slate-300 bg-white"
    >
      <Image
        source={{
          uri: imageUrl ?? "https://placehold.co/400x400",
        }}
        className="h-32 w-full"
        resizeMode="cover"
      />

      <View className="flex-1 p-3">
        <Text
          numberOfLines={2}
          ellipsizeMode="tail"
          className="font-medium"
        >
          {name}
        </Text>

        <View className="mt-auto">
          {brand && (
            <Text
              numberOfLines={1}
              className="text-sm text-slate-500"
            >
              {brand}
            </Text>
          )}

          {price != null && (
            <Text className="mt-2 font-bold">
              ₱{price.toFixed(2)}
            </Text>
          )}

          {sales != null && (
            <Text
              numberOfLines={1}
              className="mt-1 text-sm text-slate-500"
            >
              {sales} sold
            </Text>
          )}
        </View>
      </View>
    </Pressable>
  );
};
