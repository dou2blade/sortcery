export const publicKeys = {
  all: ["public"] as const,

  branches: () => [...publicKeys.all, "branches"] as const,
  branchesNearby: () => [...publicKeys.branches(), "nearby"] as const,

  productCategories: () => [...publicKeys.all, "product-categories"] as const,

  products: () => [...publicKeys.all, "products"] as const,
  productsTopGlobal: () => [...publicKeys.products(), "top-global"] as const,
  productsTopNearby: () => [...publicKeys.products(), "top-nearby"] as const,

  stats: () => [...publicKeys.all, "stats"] as const,

}
