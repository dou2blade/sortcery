import { useProductVariant } from "@/features/product-variants/hooks";
import { AdminProductVariantForm } from "@/views/admin/product-variants";
import { useLocalSearchParams } from "expo-router";

const AdminProductVariantEditPage = () => {
  const { id } = useLocalSearchParams();

  const { data } = useProductVariant(Number(id));

  if (!data?.data) return null;

  const productVariant = { ...data.data };

  return <AdminProductVariantForm productVariant={productVariant} />;
}

export default AdminProductVariantEditPage;
