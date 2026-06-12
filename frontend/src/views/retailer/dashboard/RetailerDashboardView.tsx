import { StatCard } from "@/components/cards";
import { CmsCardsContainer, CmsContainer } from "@/components/containers";
import { CmsHeader } from "@/components/headers";
import { MapView } from "@/components/maps";
import { useAuthStore } from "@/features/auth/stores";
import { useStaffBranchContextStore } from "@/features/ui/stores";
import { Text } from "react-native";
import Animated, { FadeIn } from "react-native-reanimated";

export const RetailerDashboardView = () => {
  const { user } = useAuthStore();
  const { branch } = useStaffBranchContextStore();

  if (branch === undefined) return null;
  if (!user) return null;

  return (
    <CmsContainer>
      <CmsHeader title="Dashboard" subtitle={`Welcome back, ${user?.firstName} ${user?.lastName}`} />
      <Animated.View entering={FadeIn}>
        <Text className="text-2xl font-bold p-2">{branch.storeName} - {branch.name}</Text>
        <MapView latitude={branch.latitude} longitude={branch.longitude} />
        <Text className="text-xs p-2 font-bold" numberOfLines={1} ellipsizeMode="tail">{branch.address}</Text>
      </Animated.View>
    </CmsContainer>
  );
}
