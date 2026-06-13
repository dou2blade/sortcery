import { StatCard } from "@/components/cards";
import { ProductCard } from "@/components/cards/ProductCard";
import { CmsCardsContainer, CmsContainer, ConsumerSection } from "@/components/containers";
import { HorizontalScroll } from "@/components/containers/HorizontalScroll";
import { useProductTopSellers } from "@/features/products/hooks/useProductTopSellers";
import { usePublicProductCategories, usePublicStats } from "@/features/public/hooks";
import { useCurrentLocation } from "@/hooks";
import MaterialIcons from "@expo/vector-icons/MaterialIcons";
import { useRouter } from "expo-router";
import { Pressable, Text, View } from "react-native";
import Animated, { FadeIn } from "react-native-reanimated";

const ConsumerPage = () => {
  const router = useRouter();
  const location = useCurrentLocation();

  const { data: topSellers, isLoading: topSellersLoading } = useProductTopSellers();
  const { data: productCategories, isLoading: productCategoriesLoading } = usePublicProductCategories();
  const { data: stats, isLoading: statsLoading } = usePublicStats();

  return (
    <CmsContainer>

      {/* Hero */}
      <Animated.View className="bg-green-600 rounded-2xl p-5 flex-row justify-between items-center" entering={FadeIn}>
        <View>
          <Text className="text-white text-2xl font-bold">
            Find products near you {location?.latitude} {location?.longitude}
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
        {topSellersLoading
          ? Array.from({ length: 5 }).map((_, idx) => (
              <ProductCard
                key={idx}
                loading
                name=""
              />
            ))
          : (topSellers?.data ?? []).map((product) => (
              <ProductCard
                key={product.id}
                name={product.name}
                imageUrl={product.imageUrl}
                sales={product.totalSales}
                href={`/consumer/products/${product.id}`}
              />
            ))}
          </HorizontalScroll>
      </ConsumerSection>

      <CmsCardsContainer title="Popular Near You">
        {[].slice(0, 4).map(product => (
          <StatCard
            key={product.id}
            title={product.name}
            value={product.brandName}
            href={`/consumer/products/${product.id}`}
          />
        ))}
      </CmsCardsContainer>

    </CmsContainer>
  );
}

export default ConsumerPage;
