import { ProductCard } from "@/components/cards/ProductCard";
import { CmsContainer } from "@/components/containers";
import { FilterChip, SearchFilter } from "@/components/filters";
import { usePublicBrandOptions, usePublicProductCategoryOptions, usePublicProducts } from "@/features/public/hooks";
import { useCurrentLocation } from "@/hooks";
import { toOptions } from "@/utils/forms";
import { useLocalSearchParams } from "expo-router";
import { Text, View } from "react-native";

const ConsumerProductsPage = () => {
  const location = useCurrentLocation();
  const { page = "0", search, category, brand, sort, radius } = useLocalSearchParams<{
    page?: string;
    search: string;
    category: string;
    brand: string;
    sort: string;
    radius: string;
  }>();

  const { data: products, isLoading: productsLoading } = usePublicProducts({
    page: Number(page),
    latitude: location?.latitude,
    longitude: location?.longitude,
		radius: Number(radius),
		search,
		category,
		brand,
		sort,
  });
  console.log(products)

  const { data: productCategories } = usePublicProductCategoryOptions();
  const { data: brands } = usePublicBrandOptions();

  const brandOptions = toOptions(brands?.data ?? [], (row) => row.name, (row) => row.id);
  const productCategoryOptions = toOptions(productCategories?.data ?? [], (row) => row.name, (row) => row.id);

  return (
    <CmsContainer>

      <SearchFilter name="search" placeholder="Search products..." />

      <View className="flex-row gap-2">
        <FilterChip
          label="Category"
          name="category"
          options={productCategoryOptions}
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
            { label: "Nearest Available", value: "distance" }
          ]}
        />

        <FilterChip
          label="Area"
          name="radius"
          options={[
            { label: "Within 5km", value: 5 },
            { label: "Within 15km", value: 15 },
            { label: "Within 50km", value: 50 }
          ]}
        />

      </View>

      {/* Header */}
      <View className="flex-row justify-between items-center">
        <Text className="text-lg font-bold">
          Products
        </Text>

        <Text className="text-slate-500">
          {products?.meta?.totalElements} found
        </Text>
      </View>

      {/* Grid */}
      <View className="flex-row flex-wrap gap-3 justify-between">
          {productsLoading
            ? Array.from({ length: 5 }).map((_, idx) => (
                <ProductCard
                  key={idx}
                  loading
                  name=""
                />
              ))
            : (products?.data ?? []).map((product) => (
                <ProductCard
                  key={product.id}
                  name={`${product.productName} ${product.productVariantName}`}
                  imageUrl={product.productVariantImageUrl}
                  sales={product.sales}
                  location={product.branchName}
                  price={product.price}
                  href={`/consumer/products/${product.id}`}
                />
              ))
          }
      </View>

    </CmsContainer>
  );
};

export default ConsumerProductsPage;
