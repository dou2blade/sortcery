import { usePathname, useRouter } from "expo-router";
import MaterialIcons from "@expo/vector-icons/MaterialIcons";
import Animated, {
  FadeInLeft,
  FadeOutLeft,
  useAnimatedStyle,
  withTiming,
} from "react-native-reanimated";
import { Pressable, Text, View } from "react-native";
import { NavItem } from "@/features/ui/types";
import { logout } from "@/utils/auth";
import { useStaffBranchContextStore } from "@/features/ui/stores";
import { useEffect, useRef } from "react";
import { BottomSheetModal, BottomSheetView } from "@gorhom/bottom-sheet";
import { useMyBranches } from "@/features/branches/hooks";
import { toOptions } from "@/utils/forms";

interface StaffSidebarProps {
  title: string;
  navItems: NavItem[];
  collapsed: boolean;
  onToggle?: () => void;
}

const StaffSidebar = ({
  title,
  navItems,
  collapsed,
  onToggle,
}: StaffSidebarProps) => {
  const { branch, setBranch } = useStaffBranchContextStore();
  const { data: branches } = useMyBranches();

  useEffect(() => {
    if (branch !== undefined) return;

    const defaultBranch = branches?.data?.[0];
    if (defaultBranch !== undefined) {
      setBranch(defaultBranch);
    }
  }, [branches]);

  const pathname = usePathname();
  const router = useRouter();

  const sheetRef = useRef<BottomSheetModal>(null);

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

  const branchOptions = toOptions(branches?.data ?? [], (row) => `${row.storeName} - ${row.name}`, (row) => row.id);

  return (
    <>
      <Pressable onPress={onToggle} className="h-full">
        <Animated.View
          style={viewStyle}
          className="h-full bg-green-800"
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
                  {title}
                </Animated.Text>
              )}
            </View>

            <View className="mx-4 h-px mb-3 bg-white" />

            <Pressable
              onPress={() => sheetRef.current?.present()}
              className={`
                p-4
                rounded-xl
                flex-row
                items-center
                ${collapsed ? "justify-center" : "justify-between"}
              `}
            >
              {!collapsed &&
                <Text className="text-base text-white" numberOfLines={1} ellipsizeMode="tail">
                  {branchOptions.length 
                    ? branchOptions.find((opt) => opt.value === branch?.id)?.label ?? ""
                    : "No branches assigned"
                  }
                </Text>
              }
              <MaterialIcons name="expand-more" size={16} color="white" />
            </Pressable>

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

      <BottomSheetModal
        ref={sheetRef}
        snapPoints={["25%", "50%", "90%"]}
        index={0}
      >
        <BottomSheetView className="p-4">
          {branchOptions.map((opt) => (
            <Pressable
              key={opt.value}
              onPress={() => {
                const branch = branches?.data?.find((branch) => branch.id === opt.value);
                if (branch) setBranch(branch);
                sheetRef.current?.dismiss();
              }}
              className="py-3 border-b border-gray-200"
            >
              <Text>{opt.label}</Text>
            </Pressable>
          ))}
        </BottomSheetView>
      </BottomSheetModal>
    </>
  );
}

export default StaffSidebar;
