import { useLocalSearchParams } from "expo-router";
import { DataTableColumn } from "@/features/ui/types";
import { Branch } from "@/features/branches/types";
import { useDeleteBranch, useBranches } from "@/features/branches/hooks";
import DataTable from "@/components/datatables";
import { SearchFilter, SelectFilter } from "@/components/filters";
import { StatCard } from "@/components/cards";
import { CmsHeader } from "@/components/headers";
import { useStoreStats } from "@/features/stores/hooks/useStoreStats";
import { CmsCardsContainer, CmsContainer, CmsFilterContainer, CmsFiltersContainer } from "@/components/containers";
import { useStoreOptions } from "@/features/stores/hooks/useStoreOptions";
import { toOptions } from "@/utils/forms";

const columns: DataTableColumn<Branch>[] = [
  { name: "Store Name", selector: (row) => row.storeName },
  { name: "Branch", selector: (row) => row.name },
  { name: "Personnel", selector: (row) => `${row.retailerIds.length + row.managerIds.length}` }
];

export const AdminBranchesListView = () => {
  const { 
    page = "0",
    search,
    store
  } = useLocalSearchParams<{
    page?: string;
    search: string;
    store: string;
  }>();

  const { data: branches, isLoading: branchesLoading } = useBranches({ page: Number(page), search, store });
  const { data: stats, isLoading: statsLoading } = useStoreStats();
  const { data: stores } = useStoreOptions();
  const deleteBranch = useDeleteBranch();

  const storeOptions = toOptions(stores?.data ?? [], (row) => row.name, (row) => row.id);

  return (
    <CmsContainer>
      <CmsHeader
				title="Branches"
				subtitle="Manage branches"
        addLabel="Add Branch"
				addHref="/admin/stores/branches/create"
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
            name="store"
            options={storeOptions}
            placeholder="Store"
          />
        </CmsFilterContainer>
      </CmsFiltersContainer>

      <CmsCardsContainer>
        <StatCard title="Stores" value={String(stats?.data?.totalStores ?? 0)} loading={statsLoading} href="/admin/stores" />
        <StatCard title="Branches" value={String(stats?.data?.totalBranches ?? 0)} loading={statsLoading} />
      </CmsCardsContainer>
      <DataTable columns={columns} data={branches} loading={branchesLoading} deleteQuery={deleteBranch} />
    </CmsContainer>
  );
}
