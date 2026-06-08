import { userDefaults } from "@/features/users/schemas";
import { AdminUserForm } from "@/views/admin/users";

const AdminUsersCreatePage = () => {
  return <AdminUserForm user={userDefaults} />;
}

export default AdminUsersCreatePage
