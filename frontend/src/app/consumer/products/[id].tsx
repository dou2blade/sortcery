import { useCurrentLocation } from "@/hooks";
import { useLocalSearchParams, useRouter } from "expo-router";
import { useMemo } from "react";
import { ActivityIndicator, FlatList, Pressable, Text, useWindowDimensions, View } from "react-native";

import { useGridColumns } from "@/hooks/useGridColumns";
import Animated, { FadeIn } from "react-native-reanimated";
import { ProductCard, StoreCard } from "@/components/cards";
import { usePublicBranches } from "@/features/public/hooks/usePublicBranches";
import { usePublicProductAlternatives } from "@/features/public/hooks";

const ConsumerProductPage = () => {
  const location = useCurrentLocation();
  const { width } = useWindowDimensions();
  const columns = useGridColumns();
  const router = useRouter();

  const PADDING = 24;
  const GAP = 12;

  const {
    id,
  } = useLocalSearchParams<{
    id: string;
  }>();

  const {
    data,
    isLoading,
  } = usePublicProductAlternatives(
    Number(id),
    location?.latitude!,
    location?.longitude!,
  );

  const product = data?.data?.[0];

  const min = Math.min(...(data?.data ?? []).map((item) => item.price));
  const max = Math.max(...(data?.data ?? []).map((item) => item.price));

  return (
    <FlatList
      data={data?.data}
      key={columns}
      numColumns={1}
      contentContainerStyle={{
        padding: 12,
        gap: 12,
      }}
      keyExtractor={(item) => item.id.toString()}

      ListHeaderComponent={
        <Animated.View entering={FadeIn}>
          <View className="rounded-2xl p-5 border border-slate-300 mb-3">
            <View>
              <Text className="text-2xl font-bold">
                {product?.productName} {product?.productVariantName}
              </Text>
              <Text className="text-black/70 mt-1">
                ₱{min} - ₱{max}
              </Text>
              <Text className="text-black/70">
                {data?.meta?.totalElements} listings
              </Text>
            </View>
          </View>
        </Animated.View>
      }

      renderItem={({ item }) => (
        <Animated.View className="flex-1" entering={FadeIn}>
          <StoreCard
            name={item.branchName}
            storeName={item.storeName}
            address={`₱${item.price}`}
            distance={item.distance}
            href={`/consumer/branches/${item.branchId}`}
          />
        </Animated.View>
      )}

      ListFooterComponent={
        isLoading
          ? <ActivityIndicator color="green" size="large" />
          : null
      }
    />
  );
};

export default ConsumerProductPage;
