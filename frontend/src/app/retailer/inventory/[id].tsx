import { useInventoryMovement } from "@/features/inventory-movements/hooks";
import { useStaffBranchContextStore } from "@/features/ui/stores";
import { RetailerInventoryForm } from "@/views/retailer/inventory";
import { useLocalSearchParams } from "expo-router";

const RetailerInventoryReadOnlyPage = () => {
  const { branch } = useStaffBranchContextStore();
  const { id } = useLocalSearchParams();

  const { data } = useInventoryMovement(Number(id), branch?.id);

  if (!data?.data) return null;

  return <RetailerInventoryForm inventoryMovement={data.data} />;
}

export default RetailerInventoryReadOnlyPage;
