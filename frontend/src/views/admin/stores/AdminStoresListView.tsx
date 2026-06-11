import { useLocalSearchParams } from "expo-router";
import { DataTableColumn } from "@/features/ui/types";
import { Store } from "@/features/stores/types";
import { useDeleteStore, useStores } from "@/features/stores/hooks";
import DataTable from "@/components/datatables";
import { SearchFilter } from "@/components/filters";
import { StatCard } from "@/components/cards";
import { useStoreStats } from "@/features/stores/hooks/useStoreStats";
import { CmsHeader } from "@/components/headers";
import { CmsCardsContainer, CmsContainer, CmsFilterContainer, CmsFiltersContainer } from "@/components/containers";

const columns: DataTableColumn<Store>[] = [
  { name: "Name", selector: (row) => row.name },
  { name: "Branches", selector: (row) => row.branches.length.toString() }
];

export const AdminStoresListView = () => {
  const { 
    page = "0",
    search
  } = useLocalSearchParams<{
    page?: string;
    search: string;
  }>();

  const { data: stores, isLoading: storesLoading } = useStores({ page: Number(page), search });
  const { data: stats, isLoading: statsLoading } = useStoreStats();
  const deleteStore = useDeleteStore();

  return (
    <CmsContainer>
      <CmsHeader
				title="Stores"
				subtitle="Manage stores"
        addLabel="Add Store"
				addHref="/admin/stores/create"
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
        <StatCard title="Stores" value={String(stats?.data?.totalStores ?? 0)} loading={statsLoading} />
        <StatCard title="Branches" value={String(stats?.data?.totalBranches ?? 0)} loading={statsLoading} href="/admin/stores/branches" />
      </CmsCardsContainer>
      <DataTable columns={columns} data={stores} loading={storesLoading} deleteQuery={deleteStore} />
    </CmsContainer>
  );
}
