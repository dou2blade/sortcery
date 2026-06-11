import { useLocalSearchParams } from "expo-router";
import { DataTableColumn } from "@/features/ui/types";
import { User } from "@/features/users/types";
import { useDeleteUser, useUsers } from "@/features/users/hooks";
import DataTable from "@/components/datatables";
import { SearchFilter, SelectFilter } from "@/components/filters";
import { SelectOption } from "@/features/ui/types";
import { StatCard } from "@/components/cards";
import { useUserStats } from "@/features/users/hooks/useUserStats";
import { CmsHeader } from "@/components/headers";
import { CmsCardsContainer, CmsContainer, CmsFilterContainer, CmsFiltersContainer } from "@/components/containers";

const columns: DataTableColumn<User>[] = [
  { name: "Name", selector: (row) => `${row.firstName} ${row.lastName}` },
  { name: "Email", selector: (row) => row.email },
  { name: "Role", selector: (row) => row.role }
];

const roleOptions: SelectOption[] = [
  { label: "Admin", value: "ADMIN" },
  { label: "Manager", value: "MANAGER" },
  { label: "Retailer", value: "RETAILER" },
  { label: "Consumer", value: "CONSUMER" }
];

export const AdminUsersListView = () => {
  const { 
    page = "0",
    role,
    search
  } = useLocalSearchParams<{
    page?: string;
    role: User["role"];
    search: string;
  }>();

  const { data: users, isLoading: usersLoading } = useUsers({ page: Number(page), role, search });
  const { data: stats, isLoading: statsLoading } = useUserStats();
  const deleteUser = useDeleteUser();

  return (
    <CmsContainer>
      <CmsHeader 
        title="Users" 
        subtitle="Manage users" 
        addLabel="Add User" 
        addHref="/admin/users/create" 
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
            name="role"
            options={roleOptions}
            placeholder="Role"
          />
        </CmsFilterContainer>
      </CmsFiltersContainer>

      <CmsCardsContainer>
        <StatCard title="Managers" value={String(stats?.data?.byRole.MANAGER ?? 0)} loading={statsLoading} />
        <StatCard title="Retailers" value={String(stats?.data?.byRole.RETAILER ?? 0)} loading={statsLoading} />
        <StatCard title="Consumers" value={String(stats?.data?.byRole.CONSUMER ?? 0)} loading={statsLoading} />
      </CmsCardsContainer>
      <DataTable columns={columns} data={users} loading={usersLoading} deleteQuery={deleteUser} />
    </CmsContainer>
  );
}
