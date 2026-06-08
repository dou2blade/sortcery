import { BottomSheetModal, BottomSheetView } from "@gorhom/bottom-sheet";
import { usePathname, useRouter } from "expo-router";
import { forwardRef } from "react";
import { Pressable } from "react-native";
import { Text } from "react-native-gesture-handler";

export const RowActionsSheet = forwardRef<BottomSheetModal, { id?: number }>(({ id }, ref) => {
  const router = useRouter();
  const pathname = usePathname();
  
  return (
    <BottomSheetModal
      ref={ref}
      snapPoints={["25%"]}
    >
      <BottomSheetView className="p-4">
        <Pressable
          className="py-3"
          onPress={() => router.push(`${pathname}/${id !== undefined ? id : ""}`)}
        >
          <Text>View</Text>
        </Pressable>

        <Pressable
          className="py-3"
          onPress={() => router.push(`${pathname}/${id !== undefined ? id : ""}`)}
        >
          <Text>Edit</Text>
        </Pressable>

        <Pressable
          className="py-3"
          onPress={() => {}}
        >
          <Text className="text-red-500">
            Delete
          </Text>
        </Pressable>
      </BottomSheetView>
    </BottomSheetModal>
  );
});
