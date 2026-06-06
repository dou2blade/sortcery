import { Text } from "react-native";

const FormLabel = ({ children }: { children: React.ReactNode }) => {
  return (
    <Text className="mb-0 ms-2 text-sm font-medium text-gray-700">
      {children}
    </Text>
  );
}

export default FormLabel;
