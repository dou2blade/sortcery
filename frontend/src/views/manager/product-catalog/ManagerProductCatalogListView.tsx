import { useLocalSearchParams } from "expo-router";
import { DataTableColumn } from "@/features/ui/types";
import { useDeleteProduct, useProductOptions } from "@/features/products/hooks";
import DataTable from "@/components/datatables";
import { SearchFilter, SelectFilter } from "@/components/filters";
import { CmsHeader } from "@/components/headers";
import { CmsCardsContainer, CmsContainer, CmsFilterContainer, CmsFiltersContainer } from "@/components/containers";
import { useBranchProductVariants, useDeleteBranchProductVariant } from "@/features/branch-product-variants/hooks";
import { useStaffBranchContextStore } from "@/features/ui/stores";
import { BranchProductVariant } from "@/features/branch-product-variants/types";
import { toOptions } from "@/utils/forms";
import { de } from "zod/v4/locales";
import { StatCard } from "@/components/cards";

const columns: DataTableColumn<BranchProductVariant>[] = [
  { name: "Product", selector: (row) => row.productName },
  { name: "Variant", selector: (row) => row.productVariantName },
  { name: "Price", selector: (row) => `₱${row.price}` }
];

export const ManagerProductCatalogListView = () => {
  const { 
    page = "0",
    product,
    search
  } = useLocalSearchParams<{
    page?: string;
    product: string;
    search: string;
  }>();

  const { branch } = useStaffBranchContextStore();

  const { data: branchProductVariants, isLoading: branchProductVariantsLoading } = useBranchProductVariants({ 
    page: Number(page),
		branch: branch?.id,
    product,
		search
  });
  const { data: products } = useProductOptions();
  const deleteBranchProductVariant = useDeleteBranchProductVariant();

  if (!branchProductVariants) return null;

  const productOptions = toOptions(
    products?.data ?? [],
    (row) => row.name,
    (row) => row.id
  );

  return (
    <CmsContainer>
      <CmsHeader
				title="Product Catalog"
				subtitle="Manage product catalog"
        addLabel="Add Product"
				addHref="/manager/product-catalog/create"
			/>
      <CmsFiltersContainer>
        <CmsFilterContainer flex={3}>
          <SearchFilter
            name="search"
            placeholder="Search..."
          />
        </CmsFilterContainer>
        <CmsFilterContainer>
          <SelectFilter
            name="product"
            options={productOptions}
            placeholder="Product"
          />
        </CmsFilterContainer>
      </CmsFiltersContainer>

      <CmsCardsContainer>
        <StatCard title="Products" value={productOptions.length} />
        <StatCard title="Variants" value={branchProductVariants.meta?.totalElements ?? 0} />
      </CmsCardsContainer>
      <DataTable 
        columns={columns}
        data={branchProductVariants} 
        loading={branchProductVariantsLoading}
        deleteQuery={deleteBranchProductVariant}
      />
    </CmsContainer>
  );
}
