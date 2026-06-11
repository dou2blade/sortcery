import { useProductCategory } from "@/features/product-categories/hooks";
import { AdminProductCategoryForm } from "@/views/admin/product-categories";
import { useLocalSearchParams } from "expo-router";

const AdminProductCategoryEditPage = () => {
  const { id } = useLocalSearchParams();

  const { data } = useProductCategory(Number(id));

  if (!data?.data) return null;

  const productCategory = { ...data.data };

  return <AdminProductCategoryForm productCategory={productCategory} />;
}

export default AdminProductCategoryEditPage;
