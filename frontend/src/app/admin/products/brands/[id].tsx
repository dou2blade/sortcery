import { useBrand } from "@/features/brands/hooks";
import { AdminBrandForm } from "@/views/admin/brands";
import { useLocalSearchParams } from "expo-router";

const AdminBrandEditPage = () => {
  const { id } = useLocalSearchParams();

  const { data } = useBrand(Number(id));

  if (!data?.data) return null;

  const brand = { ...data.data };

  return <AdminBrandForm brand={brand} />;
}

export default AdminBrandEditPage;
