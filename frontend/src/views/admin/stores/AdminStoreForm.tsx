import FormGroup, { FormButtons, FormContainer } from "@/components/forms";
import { CmsHeader } from "@/components/headers";
import { useCreateStore, useUpdateStore } from "@/features/stores/hooks";
import { storeToFormData, StoreFormData, StoreSchema } from "@/features/stores/schemas";
import { Store } from "@/features/stores/types";
import { useSubmitHandler } from "@/hooks";
import { toOptions } from "@/utils/forms";
import { zodResolver } from "@hookform/resolvers/zod";
import { FormProvider, useForm } from "react-hook-form";
import { ScrollView } from "react-native";

interface AdminStoreFormProps {
  store?: Store;
}

export const AdminStoreForm = ({ store }: AdminStoreFormProps) => {
  const formMethods = useForm<StoreFormData>({
    resolver: zodResolver(StoreSchema),
    defaultValues: storeToFormData(store)
  });

  const onSubmit = useSubmitHandler({
    form: formMethods,
    id: store?.id,
    entity: "store",
    create: useCreateStore(),
    update: useUpdateStore()
  });

  const branchValues = toOptions(store?.branches ?? [], (row) => row.name, (row) => row.id);

  return (
    <ScrollView className="flex-1 m-3 gap-3">
      <CmsHeader 
        title={store ? "Edit Store" : "Add Store"} 
        subtitle={store ? "Edit existing store details" : "Add a new store" }
      />     
      <FormProvider {...formMethods}>
        <FormContainer>
          <FormGroup name="name" placeholder="Store name" />
          <FormGroup
            type="list-readonly" 
            values={branchValues}
            label="Branches"
            href="/admin/stores/branches"
          />

          <FormButtons onSubmit={onSubmit} />
        </FormContainer>
      </FormProvider>
    </ScrollView>
  );
}
