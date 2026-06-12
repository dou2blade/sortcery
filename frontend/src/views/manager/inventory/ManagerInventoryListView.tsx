import { useLocalSearchParams } from "expo-router";
import { DataTableColumn } from "@/features/ui/types";
import DataTable from "@/components/datatables";
import { SearchFilter } from "@/components/filters";
import { CmsHeader } from "@/components/headers";
import { CmsContainer, CmsFilterContainer, CmsFiltersContainer } from "@/components/containers";
import { useStaffBranchContextStore } from "@/features/ui/stores";
import { InventoryMovement } from "@/features/inventory-movements/types";
import { useInventoryMovements } from "@/features/inventory-movements/hooks";

const columns: DataTableColumn<InventoryMovement>[] = [
  { name: "Date", selector: (row) => new Date(row.createdAt).toDateString() },
  { name: "Type", selector: (row) => row.type },
  { name: "Product", selector: (row) => `${row.productName} ${row.productVariantName} (${row.sku})` },
  { name: "Quantity Change", selector: (row) => `${row.quantityChange > 0 ? `+${row.quantityChange}` : row.quantityChange}` },
];

export const ManagerInventoryListView = () => {
  const { 
    page = "0",
    search
  } = useLocalSearchParams<{
    page?: string;
    search: string;
  }>();

  const { branch } = useStaffBranchContextStore();

  const { data: inventoryMovements, isLoading: inventoryMovementsLoading } = useInventoryMovements({ 
    page: Number(page),
		branch: branch?.id,
		search
  });

  if (!inventoryMovements) return null;

  return (
    <CmsContainer>
      <CmsHeader
				title="Inventory"
				subtitle="Manage stock records"
        addLabel="Add Record"
				addHref="/manager/inventory/create"
			/>
      <CmsFiltersContainer>
        <CmsFilterContainer flex={3}>
          <SearchFilter
            name="search"
            placeholder="Search..."
          />
        </CmsFilterContainer>
      </CmsFiltersContainer>

      <DataTable 
        columns={columns}
        data={inventoryMovements} 
        loading={inventoryMovementsLoading}
        disableEdit
      />
    </CmsContainer>
  );
}
