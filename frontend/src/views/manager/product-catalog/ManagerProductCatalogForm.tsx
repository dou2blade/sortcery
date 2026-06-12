import FormGroup, { FormButtons, FormContainer } from "@/components/forms";
import { CmsHeader } from "@/components/headers";
import { useCreateBranchProductVariant, useUpdateBranchProductVariant } from "@/features/branch-product-variants/hooks";
import { branchProductVariantToFormData, BranchProductVariantFormData, BranchProductVariantSchema } from "@/features/branch-product-variants/schemas";
import { BranchProductVariant } from "@/features/branch-product-variants/types";
import { useProductVariantOptions } from "@/features/product-variants/hooks/useProductVariantOptions";
import { useSubmitHandler } from "@/hooks";
import { toOptions } from "@/utils/forms";
import { zodResolver } from "@hookform/resolvers/zod";
import { FormProvider, useForm } from "react-hook-form";
import { ScrollView } from "react-native";

interface ManagerProductCatalogFormProps {
  branchProductVariant?: BranchProductVariant;
}

export const ManagerProductCatalogForm = ({ branchProductVariant }: ManagerProductCatalogFormProps) => {
  const formMethods = useForm<BranchProductVariantFormData>({
    resolver: zodResolver(BranchProductVariantSchema),
    defaultValues: branchProductVariantToFormData(branchProductVariant)
  });

  const onSubmit = useSubmitHandler({
    form: formMethods,
    id: branchProductVariant?.id,
    entity: "product",
    create: useCreateBranchProductVariant(),
    update: useUpdateBranchProductVariant()
  });

  const { data: productVariants } = useProductVariantOptions();

  const productVariantOptions = toOptions(
    productVariants?.data ?? [],
    (row) => `${row.productName} ${row.name}`,
    (row) => row.id
  );

  return (
    <ScrollView className="flex-1 m-3 gap-3">
      <CmsHeader 
        title={branchProductVariant ? "Edit Product" : "Add Product"} 
        subtitle={branchProductVariant ? "Edit existing product details" : "Add a new product" }
      />     
      <FormProvider {...formMethods}>
        <FormContainer>
          <FormGroup 
            type="select" 
            name="productVariantId" 
            label="Product" 
            options={productVariantOptions} 
            placeholder="Select a product"
          />
          <FormGroup name="sku" placeholder="Stock Keeping Unit" />
          <FormGroup name="price" placeholder="Enter product price" />
          <FormGroup name="quantity" placeholder="Enter initial stock" label="Current Stock" readOnly={!!branchProductVariant} />

          <FormButtons onSubmit={onSubmit} />
        </FormContainer>
      </FormProvider>
    </ScrollView>
  );
}
