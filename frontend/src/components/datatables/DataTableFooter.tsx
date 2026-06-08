import Feather from '@expo/vector-icons/Feather';
import { useRouter } from 'expo-router';
import { Pressable, Text, View } from "react-native";

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
  const router = useRouter();

  const start = total === 0 ? 0 : (page * size) + 1;
  const end = total === 0
    ? 0
    : (start - 1) + size >= total ? total : (start - 1) + size;
  return (
    <View className="flex-row items-center justify-end p-3 w-full rounded-b-lg border border-gray-300 bg-slate-200">
      <Text className="text-xs text-slate-500 mr-2">
        {start} - {end} of {total}
      </Text>

      <Pressable 
        disabled={page <= 0}
        onPress={() => router.setParams({ page: String(0)})}
      >
        <Feather name="chevrons-left" size={16} color={page <= 0 ? "gray" : "black"} />
      </Pressable>

      <Pressable 
        disabled={page <= 0}
        onPress={() => router.setParams({ page: String(Number(page) - 1)})}
      >
        <Feather name="chevron-left" size={16} color={page <= 0 ? "gray" : "black"} />
      </Pressable>

      <Pressable 
        disabled={page >= pageCount - 1}
        onPress={() => router.setParams({ page: String(Number(page) + 1)})}
      >
        <Feather name="chevron-right" size={16} color={page >= pageCount - 1 ? "gray" : "black"} />
      </Pressable>

      <Pressable 
        disabled={page >= pageCount - 1}
        onPress={() => router.setParams({ page: String(Number(pageCount) - 1)})}
      >
        <Feather name="chevrons-right" size={16} color={page >= pageCount - 1 ? "gray" : "black"} />
      </Pressable>
    </View>
  );
}
