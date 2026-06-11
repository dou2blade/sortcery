import { StatCard } from "@/components/cards";
import { CmsCardsContainer, CmsContainer } from "@/components/containers";
import { CmsHeader } from "@/components/headers";
import { useAuthStore } from "@/features/auth/stores";
import { useBranchStats } from "@/features/branches/hooks/useBranchStats";
import { useStaffBranchContextStore } from "@/features/ui/stores";

export const ManagerDashboardView = () => {
  const { user } = useAuthStore();
  const { branchId, name } = useStaffBranchContextStore();

  const { data: branchStats, isLoading: branchLoading } = useBranchStats(branchId);

  if (branchId === undefined) return null;
  if (branchStats?.data === undefined) return null;
  if (!user) return null;

  const {
    totalManagers,
    totalRetailers,
    totalProducts,
    weeklySales,
    monthlySales
  } = branchStats!.data!;

  return (
    <CmsContainer>
      <CmsHeader title={`${name} Dashboard`} subtitle={`Welcome back, ${user?.firstName} ${user?.lastName}`} />
      <CmsCardsContainer title="Personnel">
        <StatCard title="Managers" value={totalManagers} loading={branchLoading} />
        <StatCard title="Retailers" value={totalRetailers} loading={branchLoading} />
      </CmsCardsContainer>
      <CmsCardsContainer title="Products">
        <StatCard title="Products" value={totalProducts} loading={branchLoading} />
      </CmsCardsContainer>
      <CmsCardsContainer>
        <StatCard title="Units Sold (weekly)" value={weeklySales} loading={branchLoading} />
        <StatCard title="Units Sold (monthly)" value={monthlySales} loading={branchLoading} />
      </CmsCardsContainer>
    </CmsContainer>
  );
}
