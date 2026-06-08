import { useRouter } from "expo-router";
import { Pressable, Text } from "react-native";

export const FormCancel = () => {
  const router = useRouter();

  return (
    <Pressable 
      className="bg-gray-500 rounded-lg p-3"
      onPress={() => router.dismiss()} 
    >
      <Text className="text-white text-center">Cancel</Text>
    </Pressable>
  );
}
