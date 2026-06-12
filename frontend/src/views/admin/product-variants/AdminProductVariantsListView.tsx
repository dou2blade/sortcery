import { useLocalSearchParams } from "expo-router";
import { DataTableColumn } from "@/features/ui/types";
import { ProductVariant } from "@/features/product-variants/types";
import { useDeleteProductVariant, useProductVariants } from "@/features/product-variants/hooks";
import DataTable from "@/components/datatables";
import { SearchFilter } from "@/components/filters";
import { StatCard } from "@/components/cards";
import { CmsHeader } from "@/components/headers";
import { CmsCardsContainer, CmsContainer, CmsFilterContainer, CmsFiltersContainer } from "@/components/containers";
import { useProductStats } from "@/features/products/hooks";

const columns: DataTableColumn<ProductVariant>[] = [
  { name: "Product", selector: (row) => row.productName },
  { name: "Variant", selector: (row) => row.name }
];

export const AdminProductVariantsListView = () => {
  const { 
    page = "0",
    search
  } = useLocalSearchParams<{
    page?: string;
    search: string;
  }>();

  const { data: productVariants, isLoading: productVariantsLoading } = useProductVariants({ page: Number(page), search });
  const { data: stats, isLoading: statsLoading } = useProductStats();
  const deleteProductVariant = useDeleteProductVariant();

  return (
    <CmsContainer>
      <CmsHeader 
        title="Product Variants" 
        subtitle="Manage Product Variants" 
        addLabel="Add Product Variant" 
        addHref="/admin/products/product-variants/create" 
      />
      <CmsFiltersContainer>
        <CmsFilterContainer flex={3}>
          <SearchFilter
            name="search"
            placeholder="Search..."
          />
        </CmsFilterContainer>
      </CmsFiltersContainer>

      <CmsCardsContainer>
        <StatCard title="Products" value={String(stats?.data?.totalProducts ?? 0)} loading={statsLoading} href="/admin/products" />
        <StatCard title="Variants" value={String(stats?.data?.totalProductVariants ?? 0)} loading={statsLoading} href="/admin/products/product-variants" />
      </CmsCardsContainer>
      <CmsCardsContainer>
        <StatCard title="Brands" value={String(stats?.data?.totalBrands ?? 0)} loading={statsLoading} href="/admin/products/brands" />
        <StatCard title="Categories" value={String(stats?.data?.totalProductCategories ?? 0)} loading={statsLoading} href="/admin/products/product-categories" />
      </CmsCardsContainer>

      <DataTable columns={columns} data={productVariants} loading={productVariantsLoading} deleteQuery={deleteProductVariant} />
    </CmsContainer>
  );
}
