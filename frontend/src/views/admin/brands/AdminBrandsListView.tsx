import { useLocalSearchParams } from "expo-router";
import { DataTableColumn } from "@/features/ui/types";
import { Brand } from "@/features/brands/types";
import { useDeleteBrand, useBrands } from "@/features/brands/hooks";
import DataTable from "@/components/datatables";
import { SearchFilter } from "@/components/filters";
import { StatCard } from "@/components/cards";
import { CmsHeader } from "@/components/headers";
import { CmsCardsContainer, CmsContainer, CmsFilterContainer, CmsFiltersContainer } from "@/components/containers";
import { useProductStats } from "@/features/products/hooks";

const columns: DataTableColumn<Brand>[] = [
  { name: "Name", selector: (row) => row.name },
  { name: "Products", selector: (row) => row.products.length.toString() }
];

export const AdminBrandsListView = () => {
  const { 
    page = "0",
    search
  } = useLocalSearchParams<{
    page?: string;
    search: string;
  }>();

  const { data: brands, isLoading: brandsLoading } = useBrands({ page: Number(page), search });
  const { data: stats, isLoading: statsLoading } = useProductStats();
  const deleteBrand = useDeleteBrand();

  return (
    <CmsContainer>
      <CmsHeader 
        title="Brands" 
        subtitle="Manage brands" 
        addLabel="Add Brand" 
        addHref="/admin/products/brands/create" 
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
        <StatCard title="Brands" value={String(stats?.data?.totalBrands ?? 0)} loading={statsLoading} />
        <StatCard title="Categories" value={String(stats?.data?.totalProductCategories ?? 0)} loading={statsLoading} href="/admin/products/product-categories" />
      </CmsCardsContainer>
      <DataTable columns={columns} data={brands} loading={brandsLoading} deleteQuery={deleteBrand} />
    </CmsContainer>
  );
}
