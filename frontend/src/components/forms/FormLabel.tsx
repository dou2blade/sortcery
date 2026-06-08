import { Text } from "react-native";

export const FormLabel = ({ children, optional }: { 
  children: React.ReactNode, 
  optional?: boolean 
}) => {
  return (
    <Text className="mb-0 ms-2 text-sm font-medium text-gray-700">
      {children}
      {!optional && 
        <Text className="mb-0 ms-2 text-xs font-medium text-red-500">
          *
        </Text>
      }
    </Text>
  );
}
