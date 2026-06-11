import { StatCard } from "@/components/cards";
import { CmsCardsContainer, CmsContainer } from "@/components/containers";
import { CmsHeader } from "@/components/headers";
import { MapView } from "@/components/maps";
import { useAuthStore } from "@/features/auth/stores";
import { useBranchStats } from "@/features/branches/hooks/useBranchStats";
import { useStaffBranchContextStore } from "@/features/ui/stores";
import { Text, View } from "react-native";
import Animated, { FadeIn } from "react-native-reanimated";

export const ManagerDashboardView = () => {
  const { user } = useAuthStore();
  const { branch } = useStaffBranchContextStore();

  const { data: branchStats, isLoading: branchLoading } = useBranchStats(branch?.id);

  if (branch === undefined) return null;
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
      <CmsHeader title="Dashboard" subtitle={`Welcome back, ${user?.firstName} ${user?.lastName}`} />
      <Animated.View entering={FadeIn}>
        <Text className="text-2xl font-bold p-2">{branch.storeName} - {branch.name}</Text>
        <MapView latitude={branch.latitude} longitude={branch.longitude} />
        <Text className="text-xs p-2 font-bold" numberOfLines={1} ellipsizeMode="tail">{branch.address}</Text>
      </Animated.View>
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
