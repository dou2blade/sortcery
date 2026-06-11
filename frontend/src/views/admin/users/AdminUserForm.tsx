import FormGroup, { FormButtons, FormContainer, FormRow } from "@/components/forms";
import { CmsHeader } from "@/components/headers";
import { useCreateUser, useUpdateUser } from "@/features/users/hooks";
import { userToFormData, UserFormData, UserSchema } from "@/features/users/schemas";
import { User } from "@/features/users/types";
import { useSubmitHandler } from "@/hooks";
import { zodResolver } from "@hookform/resolvers/zod";
import { FormProvider, useForm } from "react-hook-form";
import { ScrollView } from "react-native";

interface AdminUserFormProps {
  user?: User;
}

export const AdminUserForm = ({ user }: AdminUserFormProps) => {
  const formMethods = useForm<UserFormData>({
    resolver: zodResolver(UserSchema),
    defaultValues: userToFormData(user)
  });

  const onSubmit = useSubmitHandler({
    form: formMethods,
    id: user?.id,
    entity: "user",
    create: useCreateUser(),
    update: useUpdateUser()
  });

  return (
    <ScrollView className="flex-1 m-3 gap-3">
      <CmsHeader 
        title={user ? "Edit User" : "Add User"} 
        subtitle={user ? "Edit existing user details" : "Add a new user account" }
      />     
      <FormProvider {...formMethods}>
        <FormContainer>
          <FormRow>
            <FormGroup name="firstName" placeholder="Foo" />
            <FormGroup name="middleName" placeholder="Bar" optional />
            <FormGroup name="lastName" placeholder="Baz" />
          </FormRow>
          <FormRow>
            <FormGroup name="email" placeholder="user@example.com" />
            <FormGroup name="password" secureTextEntry={true} placeholder="●●●●●●●●" />
          </FormRow>
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

          <FormButtons onSubmit={onSubmit} />
        </FormContainer>
      </FormProvider>
    </ScrollView>
  );
}
