import { View } from "react-native"
import { useLocalSearchParams } from "expo-router";
import { DataTableColumn } from "@/features/ui/types";
import { User } from "@/features/users/types";
import { useUsers } from "@/features/users/hooks";
import DataTable from "@/components/datatables";
import { SearchFilter, SelectFilter } from "@/components/filters";
import { SelectOption } from "@/features/ui/types";
import { StatCard } from "@/components/cards";
import { useUserStats } from "@/features/users/hooks/useUserStats";
import { FadeInDown, FadeInLeft, FadeInRight, FadeInUp } from "react-native-reanimated";

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

const AdminUsersPage = () => {
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

  return (
    <View className="flex-1 m-3 gap-3">
      <View className="flex-row gap-3">
        <StatCard title="Managers" value={String(stats?.data?.byRole.MANAGER ?? 0)} loading={statsLoading} fadeIn={FadeInUp} />
        <StatCard title="Retailers" value={String(stats?.data?.byRole.RETAILER ?? 0)} loading={statsLoading} fadeIn={FadeInUp} />
        <StatCard title="Consumers" value={String(stats?.data?.byRole.CONSUMER ?? 0)} loading={statsLoading} fadeIn={FadeInUp} />
      </View>
      <View className="flex-row gap-3 w-full">
        <View className="flex-[3]">
          <SearchFilter
            name="search"
            placeholder="Search..."
            fadeIn={FadeInLeft}
          />
        </View>
        <View className="flex-1">
          <SelectFilter
            name="role"
            options={roleOptions}
            placeholder="Role"
            fadeIn={FadeInRight}
          />
        </View>
      </View>
      <DataTable columns={columns} data={users} loading={usersLoading} fadeIn={FadeInDown} />
    </View>
  );
}

export default AdminUsersPage;
