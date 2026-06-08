import Toast from "react-native-toast-message";

const toast = {
  success: (text: string) =>
    Toast.show({
      type: "success",
      text1: text,
      position: "bottom"
    }),

  error: (text: string) =>
    Toast.show({
      type: "error",
      text1: text,
      position: "bottom"
    }),
};

export default toast;
