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

  cmsSuccess: (isCreate: boolean, entityName: string) =>
    Toast.show({
      type: "success",
      text1: `Successfully ${isCreate ? "created new" : "updated"} ${entityName}`,
      position: "bottom"
    }),

  cmsError: (isCreate: boolean, entityName: string) =>
    Toast.show({
      type: "success",
      text1: `Failed to ${isCreate ? "create" : "update"} ${entityName}`,
      position: "bottom"
    }),

};

export default toast;
