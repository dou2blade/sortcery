import { useStore } from "@/features/stores/hooks";
import { AdminStoreForm } from "@/views/admin/stores";
import { useLocalSearchParams } from "expo-router";

const AdminStoreEditPage = () => {
  const { id } = useLocalSearchParams();

  const { data } = useStore(Number(id));

  if (!data?.data) return null;

  return <AdminStoreForm store={data.data} />;
}

export default AdminStoreEditPage;
