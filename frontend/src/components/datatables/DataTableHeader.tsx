import { DataTableColumn } from "@/features/ui/types";
import { Text, View } from "react-native";

interface DataTableHeaderProps<T> {
  columns: DataTableColumn<T>[];
}

export const DataTableHeader = <T,>({ columns }: DataTableHeaderProps<T>) => {
  return (
    <View className="flex-row w-full rounded-t-lg border border-gray-300 bg-slate-200">
      {columns.map(({ name }, idx) => (
        <Text key={`${idx}-${name}`} className="w-full p-3 font-semibold">{name}</Text>
      ))}
    </View>
  );
}
