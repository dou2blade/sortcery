import Animated, { FadeIn } from "react-native-reanimated"

interface FormContainerProps {
  children: React.ReactNode;
  
}

export const FormContainer = ({
  children,
}: FormContainerProps) => {
  return (
    <Animated.View
      className="
        rounded-xl
        border
        border-slate-300
        bg-white
        p-4
        gap-4
      "
      entering={FadeIn}
    >
      {children}
    </Animated.View>
  );
};
