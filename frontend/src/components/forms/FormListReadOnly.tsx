import { Pressable, Text, View } from "react-native";
import MaterialIcons from '@expo/vector-icons/MaterialIcons';
import { SelectOption } from "@/features/ui/types";
import { Href, useRouter } from "expo-router";
import { FormLabel } from "./FormLabel";

interface FormListReadOnlyProps {
  label: string;
  values: SelectOption[];
  href?: Href;
}

export const FormListReadOnly = ({
  label,
  values,
  href
}: FormListReadOnlyProps) => {
  const router = useRouter();

  return (
    <View>
      <View
        className="
          flex-row
          rounded-t-xl
          border
          bg-slate-200
          p-2
          justify-between
          items-center
          border-slate-300
        "
      >
        <View className="flex-row gap-2">
          <FormLabel optional>{label}</FormLabel>
        </View>
        <View
          className="
            items-center
            rounded
            rounded-tr-xl
            p-2
            bg-green-600/70
          "
        >
          <MaterialIcons name="add" size={14} color="white" />
        </View>
      </View>
      <View className="items-stretch p-3 w-full rounded-b-lg border-b border-x border-gray-300">
        {!values.length && <Text className="text-gray-700 text-center p-3">No items</Text>}
        {values.map((v, idx) => (
          <View
            key={idx}
            className="
              w-full
              flex-row
              bg-white
              px-2
            "
          >
            <View style={{ flex: 1, padding: 12, minWidth: 0 }}>
              <Text numberOfLines={1}>
                {idx + 1}
              </Text>
            </View>
            <View style={{ flex: 14, padding: 12, minWidth: 0 }}>
              <Text numberOfLines={1}>
                {v.label}
              </Text>
            </View>
            <View style={{ flex: 1, padding: 12, minWidth: 0 }} className="items-end justify-center">
              { href &&
                <Pressable onPress={() => router.push(`${href}/${v.value}`)}>
                  <MaterialIcons name="arrow-forward" className="text-black" size={14}/>
                </Pressable>
              }
            </View>
          </View>
        )) }
      </View>
    </View>
  );
};
