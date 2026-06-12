import FormGroup, { FormButtons, FormContainer } from "@/components/forms";
import { CmsHeader } from "@/components/headers";
import { useBrandOptions } from "@/features/brands/hooks/useBrandOptions";
import { useProductCategoryOptions } from "@/features/product-categories/hooks/useProductCategoryOptions";
import { useCreateProduct, useUpdateProduct } from "@/features/products/hooks";
import { productToFormData, ProductFormData, ProductSchema } from "@/features/products/schemas";
import { Product } from "@/features/products/types";
import { useSubmitHandler } from "@/hooks";
import { toOptions } from "@/utils/forms";
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

  const { data: brands } = useBrandOptions();
  const { data: productCategories } = useProductCategoryOptions();

  const variantValues = toOptions(product?.productVariants ?? [], (row) => row.name, (row) => row.id);
  const brandOptions = toOptions(brands?.data ?? [], (row) => row.name, (row) => row.id);
  const productCategoryOptions = toOptions(productCategories?.data ?? [], (row) => row.name, (row) => row.id);

  return (
    <ScrollView className="flex-1 m-3 gap-3">
      <CmsHeader 
        title={product ? "Edit Product" : "Add Product"} 
        subtitle={product ? "Edit existing product details" : "Add a new product" }
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

          <FormGroup
            type="list-readonly"
            values={variantValues}  
            label="Product Variants"
            href="/admin/products/product-variants"
          />

          <FormButtons onSubmit={onSubmit} />
        </FormContainer>
      </FormProvider>
    </ScrollView>
  );
}
