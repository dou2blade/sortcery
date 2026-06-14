import { PublicProductParams } from "../types";

export const publicKeys = {
  all: ["public"] as const,

  branches: () => [...publicKeys.all, "branches"] as const,
  branch: (id: number) => [...publicKeys.branches(), id] as const,
  branchesStore: (search: string, id: number) => [...publicKeys.branches(), "store", id, search] as const,
  branchesNearby: () => [...publicKeys.branches(), "nearby"] as const,

  brands: () => [...publicKeys.all, "brands"] as const,
  brandsQuery: (search: string) => [...publicKeys.brands(), search] as const,
  brandOptions: () => [...publicKeys.brands(), "options"] as const,

  productCategories: () => [...publicKeys.all, "product-categories"] as const,
  productCategoryOptions: () => [...publicKeys.productCategories(), "options"] as const,

  products: () => [...publicKeys.all, "products"] as const,
  productsAlternatives: (id: number) => [...publicKeys.products(), "alternatives", id] as const,
  productsQuery: (params: PublicProductParams) => [...publicKeys.products(), params] as const,
  productsTopGlobal: () => [...publicKeys.products(), "top-global"] as const,
  productsTopNearby: () => [...publicKeys.products(), "top-nearby"] as const,

  stats: () => [...publicKeys.all, "stats"] as const,

}
