import { Pressable, Text, View, Image } from "react-native";
import { Href, useRouter } from "expo-router";

interface BrandCardProps {
  name: string;
  imageUrl?: string;
  href?: Href;
  loading?: boolean;
  width?: string;
}

export const BrandCard = ({
  name,
  imageUrl,
  href,
  loading = false,
  width = "w-full"
}: BrandCardProps) => {
  const router = useRouter();

  if (loading) {
    return (
      <View className={`${width} w-40 h-64 bg-white rounded-xl border border-slate-300 p-3`}>
        <View className="h-32 rounded-lg bg-slate-200" />
        <View className="mt-3 h-4 w-3/4 rounded bg-slate-200" />
      </View>
    );
  }

  return (
    <Pressable
      onPress={() => href && router.push(href)}
      className={`${width} h-64 overflow-hidden rounded-xl border border-slate-300 bg-white`}
    >
      <Image
        source={{
          uri: imageUrl ?? "https://placehold.co/400x400",
        }}
        className="h-48 w-full"
        resizeMode="cover"
      />

      <View className="flex-1 p-3 justify-center">
        <Text
          numberOfLines={2}
          ellipsizeMode="tail"
          className="font-medium text-center"
        >
          {name}
        </Text>
      </View>
    </Pressable>
  );
};
