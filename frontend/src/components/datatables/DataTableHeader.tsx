import { DataTableColumn } from "@/features/ui/types";
import { Text, View } from "react-native";

interface DataTableHeaderProps<T> {
  columns: DataTableColumn<T>[];
}

export const DataTableHeader = <T,>({
  columns,
}: DataTableHeaderProps<T>) => {
  return (
    <View
      className="
        flex-row
        rounded-t-xl
        border
        border-slate-300
        bg-slate-100
        px-2
      "
    >
      {columns.map((column) => (
        <View
          key={column.name}
          style={{
            flex: column.flex ?? 1,
            padding: 12,
            minWidth: 0,
          }}
        >
          <Text className="font-semibold text-slate-700">
            {column.name}
          </Text>
        </View>
      ))}

      <View
        className="items-center justify-center"
        style={{
          width: 56,
        }}
      >
        <Text className="font-semibold text-slate-700">
          Actions
        </Text>
      </View>
    </View>
  );
};
