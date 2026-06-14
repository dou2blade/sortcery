import MaterialIcons from "@expo/vector-icons/MaterialIcons";
import { Image } from "expo-image";
import { Stack, usePathname, useRouter } from "expo-router";
import { useEffect, useState } from "react";
import { Pressable, TextInput, View } from "react-native";

const ConsumerLayout = () => {
  const pathname = usePathname();
  const router = useRouter();

  const [value, setValue] = useState("");

  const isHome = pathname === "/consumer";

  useEffect(() => {
    const timeout = setTimeout(() => {
      if (isHome && value) router.push("/consumer/products");
      router.setParams({ search: value || undefined });
    }, 500);

    return () => clearTimeout(timeout);
  }, [value]);

  return (
    <View className="flex-1">
      <View className="bg-green-800 px-4 py-5 flex-row items-center gap-3">

        <Pressable
          onPress={() => {
            setValue("");
            router.push("/consumer");
          }}
        >
          <Image
            source={require("@/assets/logo.png")}
            className="mx-2"
            style={{ height: 40, width: 28 }}
          />
        </Pressable>

        <View className="flex-1">
          <View className="absolute left-3 top-3 z-10">
            <MaterialIcons name="search" size={22} color="gray" />
          </View>

          <TextInput
            value={value}
            onChangeText={setValue}
            placeholder="Search products..."
            className={`
              w-full
              rounded-xl
              border
              bg-white
              pl-10
              pr-4
              py-3
              text-base
              border-gray-300
              ${value ? "" : "text-slate-500"}
            `}
          />
        </View>
      </View>

      <View className="flex-1">
        <Stack screenOptions={{ headerShown: false }} />
      </View>

    </View>
  );
};

export default ConsumerLayout;
