export interface Branch {
  id: number;
  storeId: number;
  storeName: string;
  retailerIds: number[];
  managerIds: number[];
  name: string;
  address: string;
  latitude: number;
  longitude: number;
  createdAt: string;
  updatedAt: string;
}
