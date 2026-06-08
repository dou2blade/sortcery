import FormGroup, { FormCancel, FormContainer, FormSubmit } from "@/components/forms";
import { CmsHeader } from "@/components/headers";
import { useCreateUser, useUpdateUser } from "@/features/users/hooks";
import { userToFormData, UserFormData, UserSchema } from "@/features/users/schemas";
import { User } from "@/features/users/types";
import { mapErrors } from "@/utils/forms";
import toast from "@/utils/toast";
import { zodResolver } from "@hookform/resolvers/zod";
import { useRouter } from "expo-router";
import { FormProvider, SubmitHandler, useForm } from "react-hook-form";
import { View } from "react-native";

interface AdminUserFormProps {
  user?: User;
}

export const AdminUserForm = ({ user }: AdminUserFormProps) => {
  const formMethods = useForm<UserFormData>({
    resolver: zodResolver(UserSchema),
    defaultValues: userToFormData(user)
  });

  const router = useRouter();

  const createUser = useCreateUser();
  const updateUser = useUpdateUser();

  const onSubmit: SubmitHandler<UserFormData> = async (payload) => {
    try {
      const { errors } = user
        ? await updateUser.mutateAsync({ id: user.id, data: payload })
        : await createUser.mutateAsync(payload)

      if (errors) {
        mapErrors(formMethods.setError, errors);
      } else {
        toast.cmsSuccess(!user, "user");
        router.dismiss();
      }
    } catch (err) {
      toast.cmsError(!user, "user");
    }
  }

  return (
    <View className="flex-1 m-3 gap-3">
      <CmsHeader 
        title={user ? "Edit User" : "Add User"} 
        subtitle={user ? "Edit existing user details" : "Add a new user account" }
      />     
      <FormProvider {...formMethods}>
        <FormContainer>
          <View className="flex-row gap-3">
            <FormGroup name="firstName" placeholder="Foo" />
            <FormGroup name="middleName" placeholder="Bar" optional />
            <FormGroup name="lastName" placeholder="Baz" />
          </View>
          <View className="flex-row flex-wrap gap-3">
            <FormGroup name="email" placeholder="user@example.com" />
            <FormGroup name="password" secureTextEntry={true} placeholder="●●●●●●●●" />
          </View>
          <FormGroup 
            name="role" 
            type="select" 
            placeholder="Select a role"
            options={[
              { label: "Admin", value: "ADMIN" },
              { label: "Manager", value: "MANAGER" },
              { label: "Retailer", value: "RETAILER" }
            ]} 
          />

          <View className="flex-row justify-end gap-3">
            <FormCancel />
            <FormSubmit onSubmit={onSubmit} />
          </View>
        </FormContainer>
      </FormProvider>
    </View>
  );
}
