import FormGroup, { FormButtons, FormContainer } from "@/components/forms";
import { CmsHeader } from "@/components/headers";
import { useCreateProductVariant, useUpdateProductVariant } from "@/features/product-variants/hooks";
import { productVariantToFormData, ProductVariantFormData, ProductVariantSchema } from "@/features/product-variants/schemas";
import { ProductVariant } from "@/features/product-variants/types";
import { useProductOptions } from "@/features/products/hooks";
import { useSubmitHandler } from "@/hooks";
import { toOptions } from "@/utils/forms";
import { zodResolver } from "@hookform/resolvers/zod";
import { Image } from "expo-image";
import { FormProvider, useForm, useWatch } from "react-hook-form";
import { ScrollView } from "react-native";

interface AdminProductVariantFormProps {
  productVariant?: ProductVariant;
}

export const AdminProductVariantForm = ({ productVariant }: AdminProductVariantFormProps) => {
  const formMethods = useForm<ProductVariantFormData>({
    resolver: zodResolver(ProductVariantSchema),
    defaultValues: productVariantToFormData(productVariant)
  });

  const onSubmit = useSubmitHandler({
    form: formMethods,
    id: productVariant?.id,
    entity: "productVariant",
    create: useCreateProductVariant(),
    update: useUpdateProductVariant()
  });

  const imageUrl = useWatch({ control: formMethods.control, name: "imageUrl" });
  const { data: products } = useProductOptions();
  const productOptions = toOptions(products?.data ?? [], (row) => row.name, (row) => row.id);

  return (
    <ScrollView className="flex-1 m-3 gap-3">
      <CmsHeader 
        title={productVariant ? "Edit Product Variant" : "Add Product Variant"} 
        subtitle={productVariant ? "Edit existing Product Variant details" : "Add a new Product Variant" }
      />     
      <FormProvider {...formMethods}>
        <FormContainer>
          <FormGroup 
            type="select"  
            name="productId"
            label="Product"
            options={productOptions}
            placeholder="Select a product"
          />
          <FormGroup name="name" label="Variant" placeholder="Enter product variant" />
          <FormGroup name="imageUrl" label="Image" placeholder="Enter image URL" optional />
          <Image source={imageUrl} contentFit="cover" style={{ height: 300 }} transition={1000} />

          <FormButtons onSubmit={onSubmit} />
        </FormContainer>
      </FormProvider>
    </ScrollView>
  );
}
