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
    width: withTiming(collapsed ? 60 : 256, {
      duration: 250,
    }),
  }));

  const labelStyle = useAnimatedStyle(() => ({
    opacity: withTiming(collapsed ? 0 : 1, {
      duration: 250
    }),
  }));

  return (
    <Pressable onPress={onToggle} className="h-full">
      <Animated.View
        style={viewStyle}
        className="h-full bg-green-600"
      >
        <View className="flex-1">
          <View className="py-6 px-4">
            {collapsed ? (
              <MaterialIcons name="inventory" size={24} color="white" />
            ) : (
              <Animated.Text
                style={labelStyle}
                numberOfLines={1}
                className="font-bold text-lg text-white"
              >
                Sortcery Admin
              </Animated.Text>
            )}
          </View>

          <View className="mx-4 h-px mb-3 bg-white" />

          {navItems.map(({ label, icon, href }) => {
            const active = pathname.startsWith(href.toString());

            return (
              <Pressable
                key={`${label}-${icon}-${href}`}
                onPress={() => {
                  onToggle?.();
                  router.push(href);
                }}
                className={`flex-row items-center pl-4 py-4 gap-3 ${
                  active ? "bg-white" : ""
                }`}
              >
                <MaterialIcons name={icon} size={24} color={active ? "black" : "white"}/>

                {!collapsed && (
                  <Animated.Text 
                    entering={FadeInLeft} 
                    exiting={FadeOutLeft} 
                    className={active ? "text-black" : "text-white"}
                  >
                    {label}
                  </Animated.Text>
                )}
              </Pressable>
            );
          })}
        </View>

        <View className="mx-4 h-px bg-white" />

        <View>
          <Pressable
            onPress={logout}
            className="flex-row items-center pl-4 py-4 gap-3"
          >
            <MaterialIcons name="logout" size={24} color="white" />

            {!collapsed && (
              <Animated.Text 
                entering={FadeInLeft} 
                exiting={FadeOutLeft}
                className="text-white"
              >
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
