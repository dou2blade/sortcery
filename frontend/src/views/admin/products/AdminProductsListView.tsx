import { useLocalSearchParams } from "expo-router";
import { DataTableColumn } from "@/features/ui/types";
import { Product } from "@/features/products/types";
import { useDeleteProduct, useProducts } from "@/features/products/hooks";
import DataTable from "@/components/datatables";
import { SearchFilter } from "@/components/filters";
import { StatCard } from "@/components/cards";
import { useProductStats } from "@/features/products/hooks/useProductStats";
import { CmsHeader } from "@/components/headers";
import { CmsCardsContainer, CmsContainer, CmsFilterContainer, CmsFiltersContainer } from "@/components/containers";

const columns: DataTableColumn<Product>[] = [
  { name: "Name", selector: (row) => row.name },
  { name: "Variants", selector: (row) => row.productVariants.length.toString() }
];

export const AdminProductsListView = () => {
  const { 
    page = "0",
    search
  } = useLocalSearchParams<{
    page?: string;
    search: string;
  }>();

  const { data: products, isLoading: productsLoading } = useProducts({ page: Number(page), search });
  const { data: stats, isLoading: statsLoading } = useProductStats();
  const deleteProduct = useDeleteProduct();

  return (
    <CmsContainer>
      <CmsHeader
				title="Products"
				subtitle="Manage products"
        addLabel="Add Product"
				addHref="/admin/products/create"
			/>
      <CmsFiltersContainer>
        <CmsFilterContainer>
          <SearchFilter
            name="search"
            placeholder="Search..."
          />
        </CmsFilterContainer>
      </CmsFiltersContainer>

      <CmsCardsContainer>
        <StatCard title="Products" value={String(stats?.data?.totalProducts ?? 0)} loading={statsLoading} />
        <StatCard title="Variants" value={String(stats?.data?.totalProductVariants ?? 0)} loading={statsLoading} href="/admin/products/product-variants" />
      </CmsCardsContainer>
      <CmsCardsContainer>
        <StatCard title="Brands" value={String(stats?.data?.totalBrands ?? 0)} loading={statsLoading} href="/admin/products/brands" />
        <StatCard title="Categories" value={String(stats?.data?.totalProductCategories ?? 0)} loading={statsLoading} href="/admin/products/product-categories" />
      </CmsCardsContainer>

      <DataTable columns={columns} data={products} loading={productsLoading} deleteQuery={deleteProduct} />
    </CmsContainer>
  );
}
