export interface PublicBranchProduct {
    id: number;

    storeId: number;
    storeName: string;

    branchId: number;
    branchName: string;

    productId: number;
    productName: string;

    productVariantId: number;
    productVariantName: string;
    productVariantImageUrl: string;

    price: number;
    quantity: number;

    sales: number;
    distance: number;
}
