import { useInventoryMovement } from "@/features/inventory-movements/hooks";
import { useStaffBranchContextStore } from "@/features/ui/stores";
import { ManagerInventoryForm } from "@/views/manager/inventory";
import { useLocalSearchParams } from "expo-router";

const ManagerInventoryReadOnlyPage = () => {
  const { branch } = useStaffBranchContextStore();
  const { id } = useLocalSearchParams();

  const { data } = useInventoryMovement(Number(id), branch?.id);

  if (!data?.data) return null;

  return <ManagerInventoryForm inventoryMovement={data.data} />;
}

export default ManagerInventoryReadOnlyPage;
