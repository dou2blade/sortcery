import FormGroup, { FormButtons, FormContainer } from "@/components/forms";
import { CmsHeader } from "@/components/headers";
import { useCreateProductCategory, useUpdateProductCategory } from "@/features/product-categories/hooks";
import { productCategoryToFormData, ProductCategoryFormData, ProductCategorySchema } from "@/features/product-categories/schemas";
import { ProductCategory } from "@/features/product-categories/types";
import { useProductOptions } from "@/features/products/hooks";
import { useSubmitHandler } from "@/hooks";
import { toOptions } from "@/utils/forms";
import { zodResolver } from "@hookform/resolvers/zod";
import { FormProvider, useForm } from "react-hook-form";
import { ScrollView } from "react-native";

interface AdminProductCategoryFormProps {
  productCategory?: ProductCategory;
}

export const AdminProductCategoryForm = ({ productCategory }: AdminProductCategoryFormProps) => {
  const formMethods = useForm<ProductCategoryFormData>({
    resolver: zodResolver(ProductCategorySchema),
    defaultValues: productCategoryToFormData(productCategory)
  });

  const onSubmit = useSubmitHandler({
    form: formMethods,
    id: productCategory?.id,
    entity: "productCategory",
    create: useCreateProductCategory(),
    update: useUpdateProductCategory()
  });

  const productValues = toOptions(productCategory?.products ?? [], (row) => row.name, (row) => row.id);

  return (
    <ScrollView className="flex-1 m-3 gap-3">
      <CmsHeader 
        title={productCategory ? "Edit Product Category" : "Add Product Category"} 
        subtitle={productCategory ? "Edit existing Product Category details" : "Add a new Product Category" }
      />     
      <FormProvider {...formMethods}>
        <FormContainer>
          <FormGroup name="name" placeholder="Enter Product Category name" />
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
