import { Text, View } from "react-native";
import { FormProvider, SubmitHandler, useForm } from "react-hook-form";
import { LoginFormData, LoginResponse, LoginSchema } from "@/definitions/schemas/login";
import { zodResolver } from "@hookform/resolvers/zod";
import FormGroup from "@/components/forms/FormGroup";
import { apiPost } from "@/utils/api";
import FormSubmit from "@/components/forms/FormSubmit";
import { useEffect, useState } from "react";
import FormFeedback from "@/components/forms/FormFeedback";
import { useRouter } from "expo-router";
import { useAuthStore } from "@/stores";
import { User } from "@/definitions/types";

const Login = () => {
  const router = useRouter();
  const { login } = useAuthStore();

  const formMethods = useForm<LoginFormData>({
    resolver: zodResolver(LoginSchema),
    defaultValues: {
      email: "",
      password: "",
      role: undefined
    }
  });

  const onSubmit: SubmitHandler<LoginFormData> = async (payload) => {
    const { email, password, role } = payload;
    const { data, message } = await apiPost<LoginResponse>(`auth/login/${role.toLowerCase()}`, { email, password });
    
    if (!data) { 
      formMethods.setError("root", { message });
      return;
    }

    const { plainToken, ...user } = data;
    login(plainToken, user);

    router.replace(`/${user.role.toLowerCase()}`);
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
