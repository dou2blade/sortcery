import { useWindowDimensions } from "react-native";
import { useMemo } from "react";

export const useGridColumns = () => {
  const { width } = useWindowDimensions();

  return useMemo(() => {
    if (width >= 1200) return 5;
    if (width >= 900) return 4;
    if (width >= 600) return 3;
    return 2;
  }, [width]);
};
