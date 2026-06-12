import { useState } from "react";
import { Stack } from "expo-router";
import {
  View,
  Pressable,
  useWindowDimensions,
} from "react-native";
import MaterialIcons from "@expo/vector-icons/MaterialIcons";
import { Drawer } from "react-native-drawer-layout";
import StaffSidebar from "@/components/sidebars/StaffSidebar";
import { NavItem } from "@/features/ui/types";

const navItems: NavItem[] = [
  { label: "Dashboard", icon: "dashboard", href: "/manager/dashboard" },
  { label: "Personnel", icon: "people", href: "/manager/personnel" },
  { label: "Product Catalog", icon: "local-grocery-store", href: "/manager/product-catalog" },
  { label: "Inventory", icon: "inventory", href: "/manager/inventory" }
] as const;

export default function StaffLayout() {
  const { width } = useWindowDimensions();

  const isMobile = width < 768;
  const isTablet = width >= 768 && width < 1024;

  const [drawerOpen, setDrawerOpen] = useState(false);
  const [collapsed, setCollapsed] = useState(true);

  if (isMobile) {
    return (
      <Drawer
        open={drawerOpen}
        onOpen={() => setDrawerOpen(true)}
        onClose={() => setDrawerOpen(false)}
        drawerStyle={{ width: 256 }}
        renderDrawerContent={() => (
          <StaffSidebar navItems={navItems} collapsed={false} onToggle={() => setDrawerOpen(!drawerOpen)} />
        )}
      >
        <View className="flex-1">
          <View className="h-14 border-b border-gray-200 px-4 flex-row items-center">
            <Pressable
              onPress={() => setDrawerOpen(true)}
            >
              <MaterialIcons
                name="menu"
                size={28}
              />
            </Pressable>
          </View>

          <Stack
            screenOptions={{
              headerShown: false,
            }}
          />
        </View>
      </Drawer>
    );
  }

  if (isTablet) {
    return (
      <View className="flex-1 flex-row">
        <StaffSidebar
          navItems={navItems}
          collapsed={collapsed}
          onToggle={() =>
            setCollapsed(prev => !prev)
          }
        />

        <Pressable
          className="flex-1"
          onPress={() => {
            if (!collapsed) {
              setCollapsed(true);
            }
          }}
        >
          <Stack
            screenOptions={{
              headerShown: false,
            }}
          />
        </Pressable>
      </View>
    );
  }

  return (
    <View className="flex-1 flex-row">
      <StaffSidebar navItems={navItems} collapsed={false} />

      <View className="flex-1">
        <Stack
          screenOptions={{
            headerShown: false,
          }}
        />
      </View>
    </View>
  );
}
