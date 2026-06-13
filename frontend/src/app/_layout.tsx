import { Href, Redirect, Stack, usePathname } from "expo-router";
import { BottomSheetModalProvider } from "@gorhom/bottom-sheet";
import { GestureHandlerRootView } from "react-native-gesture-handler";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { useAuthStore } from "@/features/auth/stores";
import "./global.css";
import Toast from "react-native-toast-message";

const queryClient = new QueryClient();

const RootLayout = () => {
  const user = useAuthStore((s) => s.user);
  const hydrated = useAuthStore.persist.hasHydrated();
  const pathname = usePathname();

  if (!hydrated) return null;

  if (
    (user?.role !== "ADMIN" && pathname.startsWith("/admin"))
    || (user?.role !== "MANAGER" && pathname.startsWith("/manager"))
    || (user?.role !== "RETAILER" && pathname.startsWith("/retailer"))
  ) {
    return <Redirect href="/auth/login" />;
  }

  if (user && pathname === "/auth/login") {
    return <Redirect href={`/${user.role.toLowerCase()}` as Href} />;
  }

  return (
    <GestureHandlerRootView style={{ flex: 1 }}>
      <QueryClientProvider client={queryClient}>
        <BottomSheetModalProvider>
          <Stack screenOptions={{ headerShown: false }} />
        </BottomSheetModalProvider>
      </QueryClientProvider>
      <Toast />
    </GestureHandlerRootView>
  );
};

export default RootLayout;
