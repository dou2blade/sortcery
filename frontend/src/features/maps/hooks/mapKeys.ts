export const mapKeys = {
  all: ["maps"] as const,

  searches: () => [...mapKeys.all, "search"] as const,
  search: (query: string) => [...mapKeys.searches(), query] as const,
};
