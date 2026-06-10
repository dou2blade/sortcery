import { View } from "react-native"
import { useLocalSearchParams } from "expo-router";
import { DataTableColumn } from "@/features/ui/types";
import { Branch } from "@/features/branches/types";
import { useDeleteBranch, useBranches } from "@/features/branches/hooks";
import DataTable from "@/components/datatables";
import { SearchFilter } from "@/components/filters";
import { StatCard } from "@/components/cards";
import { CmsHeader } from "@/components/headers";
import { useStoreStats } from "@/features/stores/hooks/useStoreStats";

const columns: DataTableColumn<Branch>[] = [
  { name: "Store Name", selector: (row) => row.storeName },
  { name: "Branch", selector: (row) => row.name },
  { name: "Personnel", selector: (row) => `${row.retailerIds.length + row.managerIds.length}` }
];

export const AdminBranchesListView = () => {
  const { 
    page = "0",
    search
  } = useLocalSearchParams<{
    page?: string;
    search: string;
  }>();

  const { data: branches, isLoading: branchesLoading } = useBranches({ page: Number(page), search });
  const { data: stats, isLoading: statsLoading } = useStoreStats();
  const deleteBranch = useDeleteBranch();

  return (
    <View className="flex-1 m-3 gap-3">
      <CmsHeader
				title="Branches"
				subtitle="Manage branches"
        addLabel="Add Branch"
				addHref="/admin/stores/branches/create"
			/>
      <View className="flex-row gap-3 w-full">
        <View className="flex-[3]">
          <SearchFilter
            name="search"
            placeholder="Search..."
          />
        </View>
      </View>

      <View className="flex-row gap-3">
        <StatCard title="Stores" value={String(stats?.data?.totalStores ?? 0)} loading={statsLoading} href="/admin/stores" />
        <StatCard title="Branches" value={String(stats?.data?.totalBranches ?? 0)} loading={statsLoading} />
      </View>
      <DataTable columns={columns} data={branches} loading={branchesLoading} deleteQuery={deleteBranch} />
    </View>
  );
}
