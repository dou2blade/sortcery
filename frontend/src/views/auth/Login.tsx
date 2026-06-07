import { View } from "react-native";
import { FormProvider, SubmitHandler, useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { useRouter } from "expo-router";
import { LoginFormData, LoginSchema } from "@/features/auth/schemas";
import FormGroup, { FormFeedback, FormSubmit } from "@/components/forms";
import { login } from "@/utils/auth";

const Login = () => {
  const router = useRouter();

  const formMethods = useForm<LoginFormData>({
    resolver: zodResolver(LoginSchema),
    defaultValues: {
      email: "",
      password: "",
      role: undefined
    }
  });

  const onSubmit: SubmitHandler<LoginFormData> = async (payload) => {
    const message = await login(payload);

    if (message) { 
      formMethods.setError("root", { message });
      return;
    }

    router.replace(`/${payload.role.toLowerCase()}`);
  }

  return (
    <View className="flex-1 gap-3 justify-center items-center">
      <FormProvider {...formMethods}>
        <FormGroup name="email" placeholder="Enter your email" />
        <FormGroup name="password" secureTextEntry={true} placeholder="Enter your password" />
        <FormGroup 
          name="role" 
          type="select" 
          placeholder="Select your role"
          options={[
            { label: "Admin", value: "ADMIN" },
            { label: "Manager", value: "MANAGER" },
            { label: "Retailer", value: "RETAILER" }
          ]} 
        />
        <View className="flex flex-col items-start w-100">
          <FormFeedback name="root" />
        </View>
        <FormSubmit onSubmit={onSubmit} />
      </FormProvider>
    </View>
  );
}

export default Login;
