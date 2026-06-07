import { View } from "react-native"
import { useLocalSearchParams } from "expo-router";
import { DataTableColumn } from "@/features/ui/types";
import { User } from "@/features/users/types";
import { useUsers } from "@/features/users/hooks";
import DataTable from "@/components/datatables";

const columns: DataTableColumn<User>[] = [
  { name: "Name", selector: (row) => `${row.firstName} ${row.lastName}` },
  { name: "Email", selector: (row) => row.email },
  { name: "Role", selector: (row) => row.role }
];

const AdminUsersPage = () => {
  const { page = "0" } = useLocalSearchParams<{
    page?: string;
  }>();

  const { data: users, isLoading, error } = useUsers({ page: Number(page) });

  return (
    <View className="items-center m-3">
      <DataTable columns={columns} data={users} loading={isLoading} />
    </View>
  );
}

export default AdminUsersPage;
