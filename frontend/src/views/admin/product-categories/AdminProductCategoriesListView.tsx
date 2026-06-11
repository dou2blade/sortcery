import { useLocalSearchParams } from "expo-router";
import { DataTableColumn } from "@/features/ui/types";
import { ProductCategory } from "@/features/product-categories/types";
import { useDeleteProductCategory, useProductCategories } from "@/features/product-categories/hooks";
import DataTable from "@/components/datatables";
import { SearchFilter } from "@/components/filters";
import { StatCard } from "@/components/cards";
import { CmsHeader } from "@/components/headers";
import { CmsCardsContainer, CmsContainer, CmsFilterContainer, CmsFiltersContainer } from "@/components/containers";
import { useProductStats } from "@/features/products/hooks";

const columns: DataTableColumn<ProductCategory>[] = [
  { name: "Name", selector: (row) => row.name },
  { name: "Products", selector: (row) => row.products.length.toString() }
];

export const AdminProductCategoriesListView = () => {
  const { 
    page = "0",
    search
  } = useLocalSearchParams<{
    page?: string;
    search: string;
  }>();

  const { data: productCategories, isLoading: productCategoriesLoading } = useProductCategories({ page: Number(page), search });
  const { data: stats, isLoading: statsLoading } = useProductStats();
  const deleteProductCategory = useDeleteProductCategory();

  return (
    <CmsContainer>
      <CmsHeader 
        title="Product Categories" 
        subtitle="Manage Product Categories" 
        addLabel="Add Product Category" 
        addHref="/admin/products/product-categories/create" 
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
        <StatCard title="Brands" value={String(stats?.data?.totalBrands ?? 0)} loading={statsLoading} href="/admin/products/brands" />
        <StatCard title="Categories" value={String(stats?.data?.totalProductCategories ?? 0)} loading={statsLoading} />
      </CmsCardsContainer>
      <DataTable columns={columns} data={productCategories} loading={productCategoriesLoading} deleteQuery={deleteProductCategory} />
    </CmsContainer>
  );
}
