import { ActivityIndicator, Text, View } from "react-native"
import { DataTableColumn } from "@/features/ui/types";
import { ApiResponse } from "@/utils/api";
import { useLocalSearchParams } from "expo-router";
import { DataTableBody, DataTableFooter, DataTableHeader } from "@/components/datatables";
import Animated, { EntryOrExitLayoutType, FadeIn, FadeInDown } from "react-native-reanimated";

interface DataTableProps<T> {
  columns: DataTableColumn<T>[];
  data?: ApiResponse<T[]>;
  loading: boolean;
  fadeIn?: EntryOrExitLayoutType;
}

const DataTable = <T,>({ 
  columns, 
  data,
  loading,
  fadeIn
}: DataTableProps<T>) => {
  const { page = "0" } = useLocalSearchParams<{
    page?: string;
  }>();

  if (loading) {
    return (
      <Animated.View className="w-full" entering={fadeIn ?? FadeIn}>
        <DataTableHeader columns={columns} />
        <ActivityIndicator color="green" />
      </Animated.View>
    );
  }

  if (!data?.data || !data?.meta || !data.data.length) {
    return (
      <Animated.View className="w-full" entering={fadeIn ?? FadeIn}>
        <DataTableHeader columns={columns} />
        <Text className="w-full p-3 text-slate-500 border-x border-slate-300 text-center">
          There are no records to display
        </Text>
        <DataTableFooter 
          page={0} 
          pageCount={0} 
          total={0} 
          size={0}
        />
      </Animated.View>
    );
  }

  return (
    <Animated.View className="w-full" entering={fadeIn ?? FadeIn}>
      <DataTableHeader columns={columns} />
      <DataTableBody data={data.data} columns={columns} />
      <DataTableFooter 
        page={Number(page)} 
        pageCount={data.meta.totalPages} 
        total={data.meta.totalElements} 
        size={data.meta.size}
      />
    </Animated.View>
  );
}

export default DataTable;
