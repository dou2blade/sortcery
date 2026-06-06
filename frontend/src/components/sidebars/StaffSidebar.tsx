import { usePathname, useRouter } from "expo-router";
import MaterialIcons from "@expo/vector-icons/MaterialIcons";
import Animated, {
  FadeInLeft,
  FadeOutLeft,
  useAnimatedStyle,
  withTiming,
} from "react-native-reanimated";
import { Pressable, View } from "react-native";
import { NavItem } from "@/definitions/types";

interface StaffSidebarProps {
  routes: NavItem[];
  collapsed: boolean;
  onToggle?: () => void;
}

const StaffSidebar = ({
  routes,
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
        <View className="py-6 px-4 border-b border-gray-200">
          {collapsed ? (
            <MaterialIcons
              name="inventory"
              size={24}
            />
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

        {routes.map(({ label, icon, href }) => {
          const active = pathname === href;

          return (
            <Pressable
              key={`${label}-${icon}-${href}`}
              onPress={() => router.push(href)}
              className={`
                flex-row
                items-center
                pl-4
                py-4
                gap-3
                ${active ? "bg-green-100" : ""}
              `}
            >
              <MaterialIcons
                name={icon}
                size={24}
              />

              {!collapsed && (
                <Animated.Text
                  entering={FadeInLeft}
                  exiting={FadeOutLeft}
                >
                  {label}
                </Animated.Text>
              )}
            </Pressable>
          );
        })}
      </Animated.View>
    </Pressable>
  );
}

export default StaffSidebar;
