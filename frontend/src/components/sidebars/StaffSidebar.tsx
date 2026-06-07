import { usePathname, useRouter } from "expo-router";
import MaterialIcons from "@expo/vector-icons/MaterialIcons";
import Animated, {
  FadeInLeft,
  FadeOutLeft,
  useAnimatedStyle,
  withTiming,
} from "react-native-reanimated";
import { Pressable, View } from "react-native";
import { NavItem } from "@/features/ui/types";
import { logout } from "@/utils/auth";

interface StaffSidebarProps {
  navItems: NavItem[];
  collapsed: boolean;
  onToggle?: () => void;
}

const StaffSidebar = ({
  navItems,
  collapsed,
  onToggle,
}: StaffSidebarProps) => {
  const pathname = usePathname();
  const router = useRouter();

  const viewStyle = useAnimatedStyle(() => ({
    width: withTiming(collapsed ? 80 : 256, {
      duration: 250,
    }),
  }));

  const labelStyle = useAnimatedStyle(() => ({
    opacity: withTiming(collapsed ? 0 : 1, {
      duration: 250
    }),
  }));

  return (
    <Pressable onPress={onToggle}>
      <Animated.View
        style={viewStyle}
        className="h-full bg-white border-r border-gray-200"
      >
        {/* TOP SECTION */}
        <View className="flex-1">
          <View className="py-6 px-4 border-b border-gray-200">
            {collapsed ? (
              <MaterialIcons name="inventory" size={24} />
            ) : (
              <Animated.Text
                style={labelStyle}
                numberOfLines={1}
                className="font-bold text-lg"
              >
                Sortcery Admin
              </Animated.Text>
            )}
          </View>

          {navItems.map(({ label, icon, href }) => {
            const active = pathname === href;

            return (
              <Pressable
                key={`${label}-${icon}-${href}`}
                onPress={() => router.push(href)}
                className={`flex-row items-center pl-4 py-4 gap-3 ${
                  active ? "bg-green-100" : ""
                }`}
              >
                <MaterialIcons name={icon} size={24} />

                {!collapsed && (
                  <Animated.Text entering={FadeInLeft} exiting={FadeOutLeft}>
                    {label}
                  </Animated.Text>
                )}
              </Pressable>
            );
          })}
        </View>

        {/* BOTTOM SECTION */}
        <View className="border-t border-gray-200">
          <Pressable
            onPress={logout}
            className="flex-row items-center pl-4 py-4 gap-3"
          >
            <MaterialIcons name="logout" size={24} />

            {!collapsed && (
              <Animated.Text entering={FadeInLeft} exiting={FadeOutLeft}>
                Logout
              </Animated.Text>
            )}
          </Pressable>
        </View>
      </Animated.View>
    </Pressable>
  );
}

export default StaffSidebar;
