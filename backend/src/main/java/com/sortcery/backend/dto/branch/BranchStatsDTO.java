package com.sortcery.backend.dto.branch;

public record BranchStatsDTO(
    long totalManagers,
    long totalRetailers,
    long totalProducts,
    long weeklySales,
    long monthlySales
) {}
