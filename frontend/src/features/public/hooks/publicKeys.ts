import { PublicProductParams } from "../types";

export const publicKeys = {
  all: ["public"] as const,

  branches: () => [...publicKeys.all, "branches"] as const,
  branchesNearby: () => [...publicKeys.branches(), "nearby"] as const,

  brands: () => [...publicKeys.all, "brands"] as const,
  brandOptions: () => [...publicKeys.brands(), "options"] as const,

  productCategories: () => [...publicKeys.all, "product-categories"] as const,
  productCategoryOptions: () => [...publicKeys.productCategories(), "options"] as const,

  products: () => [...publicKeys.all, "products"] as const,
  productsQuery: (params: PublicProductParams) => [...publicKeys.products(), params] as const,
  productsTopGlobal: () => [...publicKeys.products(), "top-global"] as const,
  productsTopNearby: () => [...publicKeys.products(), "top-nearby"] as const,

  stats: () => [...publicKeys.all, "stats"] as const,

}
