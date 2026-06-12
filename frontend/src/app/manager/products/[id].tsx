import { useProduct } from "@/features/products/hooks";
import { AdminProductForm } from "@/views/admin/products";
import { useLocalSearchParams } from "expo-router";

const AdminProductEditPage = () => {
  const { id } = useLocalSearchParams();

  const { data } = useProduct(Number(id));

  if (!data?.data) return null;

  return <AdminProductForm product={data.data} />;
}

export default AdminProductEditPage;
