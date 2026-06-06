import { useRouter } from "expo-router";
import { Text, View, Button } from "react-native";

export default function Index() {
  const router = useRouter();
  return (
    <View>
      <Text>Edit src/app/index.tsx to edit this screen.</Text>
      <Button title="Login Page" onPress={() => router.push("/auth/login")}/>
    </View>
  );
}
