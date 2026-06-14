import { useLocalSearchParams } from "expo-router";
import { useMemo } from "react";
import { ActivityIndicator, FlatList, Text, useWindowDimensions, View } from "react-native";

import { useGridColumns } from "@/hooks/useGridColumns";
import { usePublicStores } from "@/features/public/hooks";
import { BrandCard } from "@/components/cards";

const ConsumerStoresPage = () => {
  const { width } = useWindowDimensions();
  const columns = useGridColumns();

  const PADDING = 24;
  const GAP = 12;
  const itemWidth = (width - PADDING * 2 - GAP * (columns - 1)) / columns;

  const { search } = useLocalSearchParams<{ search: string; }>();

  const {
    data,
    isLoading,
    fetchNextPage,
    hasNextPage,
    isFetchingNextPage,
  } = usePublicStores(search);

  const brands = useMemo(() => (
    data?.pages.flatMap(
      (page) => page.data ?? []
    ) ?? []
  ), [data]);

  const totalElements = data?.pages?.[0]?.meta?.totalElements ?? 0;

  return (
    <FlatList
      data={brands}
      key={columns}
      numColumns={columns}
      columnWrapperStyle={{
        gap: 12,
        justifyContent: "flex-start",
        marginStart: 12
      }}
      contentContainerStyle={{
        padding: 12,
        gap: 12,
      }}
      keyExtractor={(item) => item.id.toString()}

      ListHeaderComponent={
        <View className="gap-3 mb-3">

          <View className="flex-row justify-between items-center">
            <Text className="text-lg font-bold">
              Stores
            </Text>

            <Text className="text-slate-500">
              {totalElements} found
            </Text>
          </View>

        </View>
      }

      renderItem={({ item }) => (
        <View style={{ width: itemWidth }}>
          <BrandCard
            name={item.name}
            href={`consumer/stores/${item.id}`}
          />
        </View>
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

export default ConsumerStoresPage;
