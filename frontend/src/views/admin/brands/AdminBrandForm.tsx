import FormGroup, { FormButtons, FormContainer } from "@/components/forms";
import { CmsHeader } from "@/components/headers";
import { useCreateBrand, useUpdateBrand } from "@/features/brands/hooks";
import { brandToFormData, BrandFormData, BrandSchema } from "@/features/brands/schemas";
import { Brand } from "@/features/brands/types";
import { useSubmitHandler } from "@/hooks";
import { toOptions } from "@/utils/forms";
import { zodResolver } from "@hookform/resolvers/zod";
import { FormProvider, useForm } from "react-hook-form";
import { ScrollView } from "react-native";

interface AdminBrandFormProps {
  brand?: Brand;
}

export const AdminBrandForm = ({ brand }: AdminBrandFormProps) => {
  const formMethods = useForm<BrandFormData>({
    resolver: zodResolver(BrandSchema),
    defaultValues: brandToFormData(brand)
  });

  const onSubmit = useSubmitHandler({
    form: formMethods,
    id: brand?.id,
    entity: "brand",
    create: useCreateBrand(),
    update: useUpdateBrand()
  });

  const productValues = toOptions(brand?.products ?? [], (row) => row.name, (row) => row.id);

  return (
    <ScrollView className="flex-1 m-3 gap-3">
      <CmsHeader 
        title={brand ? "Edit Brand" : "Add Brand"} 
        subtitle={brand ? "Edit existing brand details" : "Add a new brand" }
      />     
      <FormProvider {...formMethods}>
        <FormContainer>
          <FormGroup name="name" placeholder="Enter brand name" />
          <FormGroup 
            type="list-readonly"  
            label="Products"
            values={productValues}
            href={"/admin/products"}
          />

          <FormButtons onSubmit={onSubmit} />
        </FormContainer>
      </FormProvider>
    </ScrollView>
  );
}
