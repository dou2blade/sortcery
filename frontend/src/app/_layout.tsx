import { useAuthStore } from "@/stores";
import { Redirect, Stack, usePathname } from "expo-router";
import "./global.css";
import { BottomSheetModalProvider } from "@gorhom/bottom-sheet";
import { GestureHandlerRootView } from "react-native-gesture-handler";

const RootLayout = () => {
  const user = useAuthStore((s) => s.user);
  const hydrated = useAuthStore.persist.hasHydrated();
  const pathname = usePathname();

  if (!hydrated) return null;
  if (!user && pathname !== "/auth/login") {
    return <Redirect href="/auth/login" />;
  }

  if (user && pathname === "/auth/login") {
    return <Redirect href="/" />;
  }

  return (
    <GestureHandlerRootView style={{ flex: 1 }}>
      <BottomSheetModalProvider>
        <Stack screenOptions={{ headerShown: false }} />
      </BottomSheetModalProvider>
    </GestureHandlerRootView>
  );
};

export default RootLayout;
