import { StatCard, StoreCard } from "@/components/cards";
import { ProductCard } from "@/components/cards/ProductCard";
import { CmsCardsContainer, CmsContainer, ConsumerSection } from "@/components/containers";
import { HorizontalScroll } from "@/components/containers/HorizontalScroll";
import { usePublicBranchesNearby, usePublicProductCategories, usePublicProductsTopGlobal, usePublicProductsTopNearby, usePublicStats } from "@/features/public/hooks";
import { useCurrentLocation } from "@/hooks";
import MaterialIcons from "@expo/vector-icons/MaterialIcons";
import { useRouter } from "expo-router";
import { Pressable, Text, View } from "react-native";
import Animated, { FadeIn } from "react-native-reanimated";

const ConsumerPage = () => {
  const router = useRouter();
  const location = useCurrentLocation();

  const { data: topGlobal, isLoading: topGlobalLoading } = usePublicProductsTopGlobal();
  const { data: topNearby, isLoading: topNearbyLoading } = usePublicProductsTopNearby(location?.latitude, location?.longitude);
  const { data: productCategories, isLoading: productCategoriesLoading } = usePublicProductCategories();
  const { data: nearbyBranches, isLoading: nearbyBranchesLoading } = usePublicBranchesNearby(location?.latitude, location?.longitude);
  const { data: stats, isLoading: statsLoading } = usePublicStats();

  return (
    <CmsContainer>

      {/* Hero */}
      <Animated.View className="bg-green-600 rounded-2xl p-5 flex-row justify-between items-center" entering={FadeIn}>
        <View>
          <Text className="text-white text-2xl font-bold">
            Find products near you
          </Text>
          <Text className="text-white/80 mt-1">
            Browse stores
          </Text>
        </View>
        <Pressable>
          <MaterialIcons name="search" size={32} color="white" />
        </Pressable>
      </Animated.View>

      <CmsCardsContainer>
        <StatCard
          title="Products"
          value={stats?.data?.totalProducts ?? ""}
          loading={statsLoading}
          href="/consumer/products"
        />
        <StatCard
          title="Brands"
          value={stats?.data?.totalBrands ?? ""}
          loading={statsLoading}
          href="/consumer/brands"
        />
        <StatCard
          title="Stores"
          value={stats?.data?.totalStores ?? ""}
          loading={statsLoading}
          href="/consumer/stores"
        />
      </CmsCardsContainer>

      <ConsumerSection title="Categories" onSeeAll={() => router.push("/consumer/product-categories")}>
        <HorizontalScroll>
          {productCategoriesLoading 
            ? Array.from({ length: 12 }, (_, i) => (
                <View key={i} className="bg-white border border-slate-300 rounded-xl px-4 py-3">
                  <View className="h-5 w-24 bg-gray-200 rounded-xl px-4 py-3 animate-pulse" />
                </View>
              )) 
            : (productCategories?.data ?? []).map((category) => (
                <Pressable
                  key={category.id}
                  className="bg-white border border-slate-300 rounded-xl px-4 py-3"
                >
                  <Text>{category.name}</Text>
                </Pressable>
              ))
           }
        </HorizontalScroll>
      </ConsumerSection>

      <ConsumerSection title="Top Selling Products">
        <HorizontalScroll>
          {topGlobalLoading
            ? Array.from({ length: 5 }).map((_, idx) => (
                <ProductCard
                  key={idx}
                  loading
                  name=""
                />
              ))
            : (topGlobal?.data ?? []).map((product) => (
                <ProductCard
                  key={product.id}
                  name={product.name}
                  imageUrl={product.imageUrl}
                  sales={product.totalSales}
                  href={`/consumer/products/${product.id}`}
                />
              ))
          }
        </HorizontalScroll>
      </ConsumerSection>

      <ConsumerSection title="Nearest stores">
        <HorizontalScroll>
          {nearbyBranchesLoading
            ? Array.from({ length: 4 }).map((_, idx) => (
                <StoreCard
                  key={idx}
                  loading
                  name=""
                />
              ))
            : (nearbyBranches?.data ?? []).map((branch) => (
                <StoreCard
                  key={branch.id}
                  name={branch.name}
                  storeName={branch.storeName}
                  address={branch.address}
                  distance={branch.distance}
                  href={`/consumer/branches/${branch.id}`}
                />
              ))
          }
        </HorizontalScroll>
      </ConsumerSection>

      <ConsumerSection title="Popular near you">
        <HorizontalScroll>
          {topNearbyLoading
            ? Array.from({ length: 5 }).map((_, idx) => (
                <ProductCard
                  key={idx}
                  loading
                  name=""
                />
              ))
            : (topNearby?.data ?? []).map((product) => (
                <ProductCard
                  key={product.id}
                  name={product.name}
                  imageUrl={product.imageUrl}
                  sales={product.totalSales}
                  href={`/consumer/products/${product.id}`}
                />
              ))
          }
        </HorizontalScroll>
      </ConsumerSection>

    </CmsContainer>
  );
}

export default ConsumerPage;
