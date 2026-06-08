import { useUser } from "@/features/users/hooks";
import { AdminUserForm } from "@/views/admin/users";
import { useLocalSearchParams } from "expo-router";

const AdminUsersEditPage = () => {
  const { id } = useLocalSearchParams();

  const { data } = useUser(Number(id));

  if (!data?.data) return null;

  const user = { ...data.data, isCreate: false }

  return <AdminUserForm user={user} />;
}

export default AdminUsersEditPage
