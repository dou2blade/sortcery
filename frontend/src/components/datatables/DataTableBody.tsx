import { DataTableColumn } from "@/features/ui/types";
import MaterialIcons from "@expo/vector-icons/MaterialIcons";
import { Pressable, Text, View } from "react-native";

interface DataTableBodyProps<T> {
  data: T[];
  columns: DataTableColumn<T>[];
  onRowAction?: (id: number) => void;
}

export const DataTableBody = <T,>({
  data,
  columns,
  onRowAction
}: DataTableBodyProps<T>) => {
  return (
    <>
      {data.map((row, rowIndex) => (
        <View
          key={rowIndex}
          className="
            flex-row
            border-x
            border-b
            border-slate-300
            bg-white
            px-2
          "
        >
          {columns.map((col) => (
            <View
              key={col.name}
              style={{
                flex: col.flex ?? 1,
                padding: 12,
                minWidth: 0,
              }}
            >
              <Text numberOfLines={1}>
                {col.selector(row)}
              </Text>
            </View>
          ))}

          <View
            className="items-center justify-center"
            style={{
              width: 56,
            }}
          >
            <Pressable
              className="rounded-lg p-2 active:bg-slate-100"
              onPress={() => onRowAction?.((row as { id: number }).id)}
            >
              <MaterialIcons
                name="more-vert"
                size={20}
                color="#64748b"
              />
            </Pressable>
          </View>

        </View>
      ))}
    </>
  );
};
