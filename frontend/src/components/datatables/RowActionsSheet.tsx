import { BottomSheetModal, BottomSheetView } from "@gorhom/bottom-sheet";
import { usePathname, useRouter } from "expo-router";
import { forwardRef } from "react";
import { Pressable } from "react-native";
import { Text } from "react-native-gesture-handler";

interface RowActionsSheetProps {
  id?: number;
  handleClose: () => void;
}

export const RowActionsSheet = forwardRef<BottomSheetModal, RowActionsSheetProps>((
  { id, handleClose },
  ref
) => {
  const router = useRouter();
  const pathname = usePathname();

  const handleNav = () => {
    router.push(`${pathname}/${id !== undefined ? id : ""}`);
    handleClose();
  }
  
  return (
    <BottomSheetModal
      ref={ref}
      snapPoints={["25%"]}
    >
      <BottomSheetView className="p-4">
        <Pressable
          className="py-3"
          onPress={handleNav}
        >
          <Text>View</Text>
        </Pressable>

        <Pressable
          className="py-3"
          onPress={handleNav}
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
