import { useBranch } from "@/features/branches/hooks";
import { useStaffBranchContextStore } from "@/features/ui/stores";
import { ManagerBranchForm } from "@/views/manager/branch";

const ManagerBranchPage = () => {
  const { branchId } = useStaffBranchContextStore();
  const { data } = useBranch(branchId);

  if (!data?.data) return null;

  return <ManagerBranchForm branch={data.data} />
}

export default ManagerBranchPage;
