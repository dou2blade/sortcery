import { useBranchProductVariant } from "@/features/branch-product-variants/hooks";
import { useStaffBranchContextStore } from "@/features/ui/stores";
import { ManagerProductCatalogForm } from "@/views/manager/product-catalog";
import { useLocalSearchParams } from "expo-router";

const ManagerProductCatalogEditPage = () => {
  const { branch } = useStaffBranchContextStore();
  const { id } = useLocalSearchParams();

  const { data } = useBranchProductVariant(Number(id), branch?.id);

  if (!data?.data) return null;

  return <ManagerProductCatalogForm branchProductVariant={data.data} />;
}

export default ManagerProductCatalogEditPage;
