import FormGroup, { FormButtons, FormContainer } from "@/components/forms";
import { CmsHeader } from "@/components/headers";
import { useCreateProduct, useUpdateProduct } from "@/features/products/hooks";
import { productToFormData, ProductFormData, ProductSchema } from "@/features/products/schemas";
import { Product } from "@/features/products/types";
import { SelectOption } from "@/features/ui/types";
import { useSubmitHandler } from "@/hooks";
import { zodResolver } from "@hookform/resolvers/zod";
import { FormProvider, useForm } from "react-hook-form";
import { ScrollView } from "react-native";

interface AdminProductFormProps {
  product?: Product;
}

export const AdminProductForm = ({ product }: AdminProductFormProps) => {
  const formMethods = useForm<ProductFormData>({
    resolver: zodResolver(ProductSchema),
    defaultValues: productToFormData(product)
  });

  const onSubmit = useSubmitHandler({
    form: formMethods,
    id: product?.id,
    entity: "product",
    create: useCreateProduct(),
    update: useUpdateProduct()
  });

  // temp
  const brandOptions: SelectOption[] = [];
  const productCategoryOptions: SelectOption[] = [];

  return (
    <ScrollView className="flex-1 m-3 gap-3">
      <CmsHeader 
        title={product ? "Edit Product" : "Add Product"} 
        subtitle={product ? "Edit existing product details" : "Add a new product account" }
      />     
      <FormProvider {...formMethods}>
        <FormContainer>
          <FormGroup 
            type="select" 
            name="brandId" 
            label="Brand" 
            options={brandOptions} 
            placeholder="Select a brand"
          />
          <FormGroup 
            type="select" 
            name="productCategoryId" 
            label="Category" 
            options={productCategoryOptions} 
            placeholder="Select a category"
          />
          <FormGroup name="name" placeholder="Product name" />

          <FormButtons onSubmit={onSubmit} />
        </FormContainer>
      </FormProvider>
    </ScrollView>
  );
}
