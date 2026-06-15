import { ProductCard } from "@/components/cards/ProductCard";
import { FilterChip, SearchFilter } from "@/components/filters";
import { usePublicBrandOptions, usePublicProductCategoryOptions, usePublicProducts } from "@/features/public/hooks";
import { useCurrentLocation } from "@/hooks";
import { toOptions } from "@/utils/forms";
import { useLocalSearchParams } from "expo-router";
import { useMemo } from "react";
import { ActivityIndicator, FlatList, Text, useWindowDimensions, View } from "react-native";

import { useGridColumns } from "@/hooks/useGridColumns";
import Animated, { FadeIn } from "react-native-reanimated";

const ConsumerProductsPage = () => {
  const location = useCurrentLocation();
  const { width } = useWindowDimensions();
  const columns = useGridColumns();

  const PADDING = 24;
  const GAP = 12;
  const itemWidth = (width - PADDING * 2 - GAP * (columns - 1)) / columns;

  const {
    search,
    category,
    brand,
    sort,
    radius,
  } = useLocalSearchParams<{
    search: string;
    category: string;
    brand: string;
    sort: string;
    radius: string;
  }>();

  const {
    data,
    isLoading,
    fetchNextPage,
    hasNextPage,
    isFetchingNextPage,
  } = usePublicProducts({
    latitude: location?.latitude,
    longitude: location?.longitude,
    radius: Number(radius),
    search,
    category,
    brand,
    sort,
  });

  const { data: productCategories } = usePublicProductCategoryOptions();

  const { data: brands } = usePublicBrandOptions();

  const products = useMemo(() => (
    data?.pages.flatMap(
      (page) => page.data ?? []
    ) ?? []
  ), [data]);

  const totalElements =
    data?.pages?.[0]?.meta?.totalElements ?? 0;

  const brandOptions = toOptions(
    brands?.data ?? [],
    (r) => r.name,
    (r) => r.id
  );

  const categoryOptions = toOptions(
    productCategories?.data ?? [],
    (r) => r.name,
    (r) => r.id
  );

  return (
    <FlatList
      data={products}
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
        <Animated.View entering={FadeIn}>
          <View className="gap-3 mb-3">

            <View className="flex-row gap-2">
              <FilterChip
                label="Category"
                name="category"
                options={categoryOptions}
              />

              <FilterChip
                label="Brand"
                name="brand"
                options={brandOptions}
              />

              <FilterChip
                label="Sort"
                name="sort"
                options={[
                  { label: "Top Sellers", value: "sales" },
                  { label: "Price: Low to High", value: "asc" },
                  { label: "Price: High to Low", value: "desc" },
                  { label: "Nearest", value: "distance" },
                ]}
              />

              <FilterChip
                label="Area"
                name="radius"
                options={[
                  { label: "5km", value: 5 },
                  { label: "15km", value: 15 },
                  { label: "50km", value: 50 },
                ]}
              />
            </View>

            <View className="flex-row justify-between items-center">
              <Text className="text-lg font-bold">
                Products
              </Text>

              <Text className="text-slate-500">
                {totalElements} found
              </Text>
            </View>

          </View>
        </Animated.View>
      }

      renderItem={({ item }) => (
        <Animated.View style={{ width: itemWidth }} entering={FadeIn}>
          <ProductCard
            name={`${item.productName} ${item.productVariantName}`}
            imageUrl={item.productVariantImageUrl}
            sales={item.sales}
            location={item.branchName}
            price={item.price}
            href={`/consumer/products/${item.productVariantId}`}
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

export default ConsumerProductsPage;
