import { usePublicBrandOptions, usePublicProductCategoryOptions, usePublicStore, usePublicProducts } from "@/features/public/hooks";
import { useCurrentLocation } from "@/hooks";
import { useLocalSearchParams, useRouter } from "expo-router";
import { useMemo } from "react";
import { ActivityIndicator, FlatList, Pressable, Text, useWindowDimensions, View } from "react-native";

import { useGridColumns } from "@/hooks/useGridColumns";
import Animated, { FadeIn } from "react-native-reanimated";
import { StoreCard } from "@/components/cards";
import { usePublicBranches } from "@/features/public/hooks/usePublicBranches";

const ConsumerStorePage = () => {
  const location = useCurrentLocation();
  const { width } = useWindowDimensions();
  const columns = useGridColumns();
  const router = useRouter();

  const PADDING = 24;
  const GAP = 12;
  const itemWidth = (width - PADDING * 2 - GAP * (columns - 1)) / columns;

  const {
    id,
    search,
  } = useLocalSearchParams<{
    id: string;
    search: string;
  }>();

  const {
    data,
    isLoading,
    fetchNextPage,
    hasNextPage,
    isFetchingNextPage,
  } = usePublicBranches(
    search,
    Number(id),
    location?.latitude!,
    location?.longitude!,
  );

  const { data: store } = usePublicStore(Number(id));

  const branches = useMemo(() => (
    data?.pages.flatMap(
      (page) => page.data ?? []
    ) ?? []
  ), [data]);

  return (
    <FlatList
      data={branches}
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
                {store?.data?.name}
              </Text>
              <Text className="text-black/70 mt-1">
                {store?.data?.branches.length} Branches
              </Text>
            </View>
          </View>
        </Animated.View>
      }

      renderItem={({ item }) => (
        <Animated.View className="flex-1" entering={FadeIn}>
          <StoreCard
            name={item.name}
            storeName={item.storeName}
            address={item.address}
            distance={item.distance}
            href={`/consumer/branches/${item.id}`}
          />
        </Animated.View>
      )}

      ListFooterComponent={
        isLoading || isFetchingNextPage
          ? <ActivityIndicator color="green" size="large" />
          : null
      }

      onEndReached={() => {
        if (hasNextPage && !isFetchingNextPage) {
          fetchNextPage();
        }
      }}

      onEndReachedThreshold={0.5}
    />
  );
};

export default ConsumerStorePage;
