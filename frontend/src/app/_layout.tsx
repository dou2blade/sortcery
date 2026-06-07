import { Redirect, Stack, usePathname } from "expo-router";
import { BottomSheetModalProvider } from "@gorhom/bottom-sheet";
import { GestureHandlerRootView } from "react-native-gesture-handler";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { useAuthStore } from "@/features/auth/stores";
import "./global.css";

const queryClient = new QueryClient();

const RootLayout = () => {
  const user = useAuthStore((s) => s.user);
  const hydrated = useAuthStore.persist.hasHydrated();
  const pathname = usePathname();

  if (!hydrated) return null;
  if (!user && pathname !== "/auth/login") {
    return <Redirect href="/auth/login" />;
  }

  if (user && pathname === "/auth/login") {
    return <Redirect href={`/${user.role.toLowerCase()}`} />;
  }

  return (
    <GestureHandlerRootView style={{ flex: 1 }}>
      <QueryClientProvider client={queryClient}>
        <BottomSheetModalProvider>
          <Stack screenOptions={{ headerShown: false }} />
        </BottomSheetModalProvider>
      </QueryClientProvider>
    </GestureHandlerRootView>
  );
};

export default RootLayout;
