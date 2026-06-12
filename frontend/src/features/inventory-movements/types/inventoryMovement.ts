export interface InventoryMovement {
  id: number;

  productId: number;
  productName: string;
  
  productVariantId: number;
  productVariantName: string;

  branchProductVariantId: number;
  sku: string;

  type: "STOCK_IN" | "TRANSFER_IN" | "ADJUSTMENT_IN" | "RETURN" | "TRANSFER_OUT" | "ADJUSTMENT_OUT" | "SALE" | "DAMAGED" | "EXPIRED";
  quantityChange: number;
  newQuantity: number;
  notes: string;

  createdById: number;
  createdByEmail: string;
  createdAt: string;
}
