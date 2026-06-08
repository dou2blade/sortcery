import { DataTableColumn } from "@/features/ui/types";
import { Text, View } from "react-native";

interface DataTableBodyProps<T> {
  data: T[];
  columns: DataTableColumn<T>[];
}

export const DataTableBody = <T,>({
  data,
  columns,
}: DataTableBodyProps<T>) => {
  return (
    <>
      {data.map((row, rowIndex) => (
        <View
          key={rowIndex}
          className="flex-row w-full border border-x-slate-300 border-y-slate-200"
        >
          {columns.map((col) => (
            <View
              key={col.name}
              style={{
                flex: col.flex ?? 1,
                padding: 12,
                minWidth: 0
              }}
            >
              <Text>
                {col.selector(row)}
              </Text>
            </View>
          ))}
        </View>
      ))}
    </>
  );
};
