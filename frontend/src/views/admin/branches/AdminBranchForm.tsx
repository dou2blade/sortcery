import FormGroup, { FormButtons, FormContainer, FormLabel, FormRow } from "@/components/forms";
import { CmsHeader } from "@/components/headers";
import { MapSearch, MapView } from "@/components/maps";
import { useCreateBranch, useUpdateBranch } from "@/features/branches/hooks";
import { branchToFormData, BranchFormData, BranchSchema } from "@/features/branches/schemas";
import { Branch } from "@/features/branches/types";
import { useStoreOptions } from "@/features/stores/hooks/useStoreOptions";
import { useSubmitHandler } from "@/hooks";
import { toOptions } from "@/utils/forms";
import { zodResolver } from "@hookform/resolvers/zod";
import { FormProvider, useForm } from "react-hook-form";
import { View } from "react-native";

interface AdminBranchFormProps {
  branch?: Branch;
}

export const AdminBranchForm = ({ branch }: AdminBranchFormProps) => {
  const formMethods = useForm<BranchFormData>({
    resolver: zodResolver(BranchSchema),
    defaultValues: branchToFormData(branch)
  });

  const onSubmit = useSubmitHandler({
    form: formMethods,
    id: branch?.id,
    entity: "branch",
    create: useCreateBranch(),
    update: useUpdateBranch()
  });

  const longitude = formMethods.watch("longitude");
  const latitude = formMethods.watch("latitude");

  const { data: stores } = useStoreOptions();
  const storeOptions = toOptions(stores?.data ?? [], (row) => row.name, (row) => row.id);

  return (
    <View className="flex-1 m-3 gap-3">
      <CmsHeader 
        title={branch ? "Edit Branch" : "Add Branch"} 
        subtitle={branch ? "Edit existing branch details" : "Add a new branch account" }
      />     
      <FormProvider {...formMethods}>
        <FormContainer>
          <FormRow>
            <FormGroup 
              name="storeId" 
              label="Store"
              type="select" 
              placeholder="Select a role"
              options={storeOptions} 
            />
          </FormRow>
          <FormGroup name="name" label="Branch Name" placeholder="Branch name" />
          <FormRow>
            <View style={{ flex: 1 }}>
              <FormLabel>Address</FormLabel>
              <MapSearch />
            </View>
          </FormRow>
          <MapView latitude={latitude ?? 0} longitude={longitude ?? 0} />
          <FormButtons onSubmit={onSubmit} />
        </FormContainer>
      </FormProvider>
    </View>
  );
}
