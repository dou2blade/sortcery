import Feather from '@expo/vector-icons/Feather';
import { View } from "react-native";

interface DataTableFooterProps {
  page: number;
  pageCount: number;
  total: number;
  size: number;
}

export const DataTableFooter = ({ 
  page, 
  pageCount,
  total,
  size
}: DataTableFooterProps) => {
  return (
    <View className="flex-row justify-end p-3 w-full rounded-b-lg border-t border-slate-300 bg-slate-200">
      <Feather name="chevrons-left" size={16} color="gray" />
      <Feather name="chevron-left" size={16} color="gray" />
      <Feather name="chevron-right" size={16} color="gray" />
      <Feather name="chevrons-right" size={16} color="gray" />
    </View>
  );
}
