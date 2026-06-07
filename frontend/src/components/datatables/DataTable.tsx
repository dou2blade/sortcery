import { ActivityIndicator, Text, View } from "react-native"
import { DataTableColumn } from "@/features/ui/types";
import { ApiResponse } from "@/utils/api";
import { useLocalSearchParams } from "expo-router";
import { DataTableBody, DataTableFooter, DataTableHeader } from "@/components/datatables";

interface DataTableProps<T> {
  columns: DataTableColumn<T>[];
  data?: ApiResponse<T[]>;
  loading: boolean;
}

const DataTable = <T,>({ 
  columns, 
  data,
  loading,
}: DataTableProps<T>) => {
  const { page = "1" } = useLocalSearchParams<{
    page?: string;
  }>();

  if (loading) {
    return (
      <View className="w-full">
        <DataTableHeader columns={columns} />
        <ActivityIndicator color="green" />
      </View>
    );
  }

  if (!data?.data || !data?.meta) {
    return (
      <View className="w-full">
        <DataTableHeader columns={columns} />
        <Text className="w-full p-3 text-slate-500 border-x border-slate-300 text-center">
          There are no records to display
        </Text>
        <DataTableFooter 
          page={1} 
          pageCount={1} 
          total={0} 
          size={0}
        />
      </View>
    );
  }

  return (
    <View className="w-full">
      <DataTableHeader columns={columns} />
      <DataTableBody data={data.data} columns={columns} />
      <DataTableFooter 
        page={Number(page)} 
        pageCount={data.meta.totalPages} 
        total={data.meta.totalElements} 
        size={data.meta.size}
      />

    </View>
  );
}

export default DataTable;
