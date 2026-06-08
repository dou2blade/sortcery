import Animated, { EntryOrExitLayoutType, FadeIn } from "react-native-reanimated"

interface FormContainerProps {
  children: React.ReactNode;
  fadeIn?: EntryOrExitLayoutType;
}

export const FormContainer = ({
  children,
  fadeIn,
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
      entering={fadeIn ?? FadeIn}
    >
      {children}
    </Animated.View>
  );
};
