export interface PublicProductParams {
  page: number;
  search: string;
  latitude?: number;
  longitude?: number;
  category: string;
  brand: string;
  sort: string;
  radius: number;
}
