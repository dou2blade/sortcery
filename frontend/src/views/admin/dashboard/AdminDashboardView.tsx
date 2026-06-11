import { StatCard } from "@/components/cards";
import { CmsCardsContainer, CmsContainer } from "@/components/containers";
import { CmsHeader } from "@/components/headers";
import { useAuthStore } from "@/features/auth/stores";
import { useProductStats } from "@/features/products/hooks";
import { useStoreStats } from "@/features/stores/hooks";
import { useUserStats } from "@/features/users/hooks";

export const AdminDashboardView = () => {
  const { user } = useAuthStore();

  const { data: productStats, isLoading: productsLoading } = useProductStats();
  const { data: userStats, isLoading: usersLoading } = useUserStats();
  const { data: storeStats, isLoading: storesLoading } = useStoreStats();

  if (!user) return null;

  return (
    <CmsContainer>
      <CmsHeader title="Dashboard" subtitle={`Welcome back, ${user?.firstName} ${user?.lastName}`} />
      <CmsCardsContainer title="User Overview">
        <StatCard title="Managers" value={String(userStats?.data?.byRole.MANAGER ?? 0)} loading={usersLoading} href="/admin/users?role=MANAGER" />
        <StatCard title="Retailers" value={String(userStats?.data?.byRole.RETAILER ?? 0)} loading={usersLoading} href="/admin/users?role=RETAILER" />
        <StatCard title="Consumers" value={String(userStats?.data?.byRole.CONSUMER ?? 0)} loading={usersLoading} href="/admin/users?role=CONSUMER" />
      </CmsCardsContainer>
      <CmsCardsContainer title="Store Overview">
        <StatCard title="Stores" value={String(storeStats?.data?.totalStores ?? 0)} loading={storesLoading} href="/admin/stores" />
        <StatCard title="Branches" value={String(storeStats?.data?.totalBranches ?? 0)} loading={storesLoading} href="/admin/stores/branches" />
      </CmsCardsContainer>
      <CmsCardsContainer title="Product Overview">
        <StatCard title="Products" value={productStats?.data?.totalProducts ?? 0} loading={productsLoading} href="/admin/products" />
        <StatCard title="Categories" value={productStats?.data?.totalProductCategories ?? 0} loading={productsLoading} href="/admin/products/product-categories" />
        <StatCard title="Brands" value={productStats?.data?.totalBrands ?? 0} loading={productsLoading} href="/admin/products/brands" />
      </CmsCardsContainer>
    </CmsContainer>
  );
}
