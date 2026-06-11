import { useBranch } from "@/features/branches/hooks";
import { useStaffBranchContextStore } from "@/features/ui/stores";
import { ManagerPersonnelForm } from "@/views/manager/personnel";
import { useEffect } from "react";

const ManagerPersonnelPage = () => {
  const { branch, setBranch } = useStaffBranchContextStore();

  const { data: freshBranch } = useBranch(branch?.id);

  useEffect(() => {
    if (freshBranch?.data) setBranch(freshBranch.data);
  }, [freshBranch]);

  if (!freshBranch?.data) return null;

  return <ManagerPersonnelForm branch={freshBranch.data} />
}

export default ManagerPersonnelPage;
