import { BottomSheetModal, BottomSheetView } from "@gorhom/bottom-sheet";
import { usePathname, useRouter } from "expo-router";
import { forwardRef } from "react";
import { Pressable } from "react-native";
import { Text } from "react-native-gesture-handler";

interface RowActionsSheetProps {
  id?: number;
  handleClose: () => void;
  handleDelete: (id: number) => void;
}

export const RowActionsSheet = forwardRef<BottomSheetModal, RowActionsSheetProps>((
  { id, handleClose, handleDelete },
  ref
) => {
  const router = useRouter();
  const pathname = usePathname();

  const handleNav = (view: boolean) => {
    if (id === undefined) return;
    router.push(`${pathname}/${id}${view ? "?view=1" : ""}`);
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
          onPress={() => handleNav(true)}
        >
          <Text>View</Text>
        </Pressable>

        <Pressable
          className="py-3"
          onPress={() => handleNav(false)}
        >
          <Text>Edit</Text>
        </Pressable>

        <Pressable
          className="py-3"
          onPress={() => id !== undefined ? handleDelete(id) : null}
        >
          <Text className="text-red-500">
            Delete
          </Text>
        </Pressable>
      </BottomSheetView>
    </BottomSheetModal>
  );
});
