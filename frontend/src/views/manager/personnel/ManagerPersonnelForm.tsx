import FormGroup, { FormButtons, FormContainer } from "@/components/forms"
import { CmsHeader } from "@/components/headers"
import { useAuthStore } from "@/features/auth/stores"
import { useUpdatePersonnelBranch } from "@/features/branches/hooks/useUpdatePersonnelBranch"
import { branchToManagerBranchPersonnelFormData, ManagerBranchPersonnelFormData, ManagerBranchPersonnelSchema } from "@/features/branches/schemas"
import { Branch } from "@/features/branches/types"
import { useUserOptions } from "@/features/users/hooks"
import { useSubmitHandler } from "@/hooks"
import { toOptions } from "@/utils/forms"
import { zodResolver } from "@hookform/resolvers/zod"
import { FormProvider, useForm } from "react-hook-form"
import { ScrollView } from "react-native"

interface ManagerPersonnelFormProps {
  branch: Branch;
}

export const ManagerPersonnelForm = ({ branch }: ManagerPersonnelFormProps) => {
  const { user } = useAuthStore();
  const domain = user?.email.split("@").pop();
  
  const formMethods = useForm<ManagerBranchPersonnelFormData>({
    resolver: zodResolver(ManagerBranchPersonnelSchema),
    defaultValues: branchToManagerBranchPersonnelFormData(branch)
  });

  const onSubmit = useSubmitHandler({
    form: formMethods,
    id: branch?.id,
    entity: "branch",
    update: useUpdatePersonnelBranch()
  });

  const { data: users } = useUserOptions(domain);

  const retailerOptions = toOptions(
    users?.data?.RETAILER ?? [],
    (row) => row.email, 
    (row) => row.id
  );
  const managerOptions = toOptions(
    users?.data?.MANAGER ?? [],
    (row) => row.email, 
    (row) => row.id
  );

  return (
    <ScrollView className="flex-1 m-3 gap-3">
      <CmsHeader 
        title="Manage Personnel" 
        subtitle="Assign or dismiss users"
      />     
      <FormProvider {...formMethods}>
        <FormContainer>
          <FormGroup type="list" label="Managers" name="managerIds" options={managerOptions} href="/admin/users" optional />
          <FormGroup type="list" label="Retailers" name="retailerIds" options={retailerOptions} href="/admin/users" optional />

          <FormButtons onSubmit={onSubmit} />
        </FormContainer>
      </FormProvider>
    </ScrollView>
  )
}
