import { useBranch } from "@/features/branches/hooks";
import { AdminBranchForm } from "@/views/admin/branches";
import { useLocalSearchParams } from "expo-router";

const AdminBranchEditPage = () => {
  const { id } = useLocalSearchParams();

  const { data } = useBranch(Number(id));

  if (!data?.data) return null;

  return <AdminBranchForm branch={data.data} />;
}

export default AdminBranchEditPage;
