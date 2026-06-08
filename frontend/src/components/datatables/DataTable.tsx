import { ActivityIndicator, Text, View } from "react-native"
import { DataTableColumn } from "@/features/ui/types";
import { ApiResponse } from "@/utils/api";
import { useLocalSearchParams } from "expo-router";
import { DataTableBody, DataTableFooter, DataTableHeader, RowActionsSheet } from "@/components/datatables";
import Animated, { EntryOrExitLayoutType, FadeIn } from "react-native-reanimated";
import { BottomSheetModal } from "@gorhom/bottom-sheet";
import { useRef, useState } from "react";

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
  const [selectedId, setSelectedId] = useState<number | undefined>(undefined);
  const { page = "0" } = useLocalSearchParams<{
    page?: string;
  }>();

  const sheetRef = useRef<BottomSheetModal>(null);

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

  const handleRowAction = (id: number) => {
    setSelectedId(id);
    sheetRef.current?.present();
  }

  return (
    <Animated.View className="w-full" entering={fadeIn ?? FadeIn}>
      <DataTableHeader columns={columns} />
      <DataTableBody 
        data={data.data} 
        columns={columns}
        onRowAction={handleRowAction}
      />
      <DataTableFooter 
        page={Number(page)} 
        pageCount={data.meta.totalPages} 
        total={data.meta.totalElements} 
        size={data.meta.size}
      />

      <RowActionsSheet 
        ref={sheetRef} 
        id={selectedId} 
        handleClose={() => sheetRef.current?.dismiss()}
      />
    </Animated.View>
  );
}

export default DataTable;
