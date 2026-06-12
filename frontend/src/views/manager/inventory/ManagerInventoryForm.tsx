import FormGroup, { FormButtons, FormContainer } from "@/components/forms";
import { CmsHeader } from "@/components/headers";
import { useAuthStore } from "@/features/auth/stores";
import { useBranchProductVariantOptions } from "@/features/branch-product-variants/hooks/useBranchProductVariantOptions";
import { useCreateInventoryMovement } from "@/features/inventory-movements/hooks";
import { InventoryMovementFormData, InventoryMovementSchema, inventoryMovementToFormData } from "@/features/inventory-movements/schemas";
import { InventoryMovement } from "@/features/inventory-movements/types";
import { useStaffBranchContextStore } from "@/features/ui/stores";
import { useSubmitHandler } from "@/hooks";
import { toOptions } from "@/utils/forms";
import { zodResolver } from "@hookform/resolvers/zod";
import { useEffect } from "react";
import { FormProvider, useForm } from "react-hook-form";
import { ScrollView, Text } from "react-native";

interface ManagerInventoryFormProps {
  inventoryMovement?: InventoryMovement;
}

export const ManagerInventoryForm = ({ inventoryMovement }: ManagerInventoryFormProps) => {
  const { user } = useAuthStore();
  const { branch } = useStaffBranchContextStore();

  const formMethods = useForm<InventoryMovementFormData>({
    resolver: zodResolver(InventoryMovementSchema),
    defaultValues: inventoryMovementToFormData(inventoryMovement)
  });

  const onSubmit = useSubmitHandler({
    form: formMethods,
    entity: "record",
    create: useCreateInventoryMovement(),
  });

  useEffect(() => {
    if (inventoryMovement || !user) return;
    formMethods.setValue("createdById", user?.id);
  }, [])

  const { data: branchProductVariants } = useBranchProductVariantOptions(branch?.id);

  const branchProductVariantOptions = toOptions(
    branchProductVariants?.data ?? [],
    (row) => `${row.productName} ${row.productVariantName} (${row.sku})`,
    (row) => row.id
  );

  return (
    <ScrollView className="flex-1 m-3 gap-3">
      <CmsHeader 
        title={inventoryMovement ? "View Record" : "Add Record"} 
        subtitle={inventoryMovement ? "View stock record" : "Add a new record" }
      />     
      <FormProvider {...formMethods}>
        <FormContainer>
          <FormGroup
            type="select"
            name="branchProductVariantId"
            label="Product"
            options={branchProductVariantOptions}
            readOnly={!!inventoryMovement}
          />
          <FormGroup
            type="select"
            name="type"
            options={[
              { label: "Stock In", value: "STOCK_IN" },
              { label: "Transfer In", value: "TRANSFER_IN" },
              { label: "Adjustment In", value: "ADJUSTMENT_IN" },
              { label: "Return", value: "RETURN" },

              { label: "Transfer Out", value: "TRANSFER_OUT" },
              { label: "Adjustment Out", value: "ADJUSTMENT_OUT" },
              { label: "Sale", value: "SALE" },
              { label: "Damaged", value: "DAMAGED" },
              { label: "Expired", value: "EXPIRED" },
            ]}
            readOnly={!!inventoryMovement}
          />

          <FormGroup 
            name="quantityChange" 
            keyboardType="numeric" 
            readOnly={!!inventoryMovement}
          />

          <FormGroup
            name="notes" 
            readOnly={!!inventoryMovement}
          />

          {inventoryMovement && <Text className="font-bold">Audited by {inventoryMovement.createdByEmail}</Text>}

          <FormButtons onSubmit={onSubmit} />
        </FormContainer>
      </FormProvider>
    </ScrollView>
  );
}
