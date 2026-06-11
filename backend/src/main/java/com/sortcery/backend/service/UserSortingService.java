package com.sortcery.backend.service;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.sortcery.backend.dto.product.ProductResponseDTO;
import com.sortcery.backend.model.BranchProductVariant;
import com.sortcery.backend.model.Product;
import com.sortcery.backend.model.ProductVariant;
import com.sortcery.backend.repository.ProductRepository;



@Service
public class UserSortingService {
    // Dependency Injection of ProductRepository to access product data
    private final ProductRepository productRepository;

    public UserSortingService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Service methods
    // Default sorting by date added (newest first)
    public List<ProductResponseDTO> defaultSorting() {
        List<Product> products = productRepository.findAll();
        List<Product> sortedProducts = defaultSearchPageSort(products);
        return sortedProducts.stream().map(ProductResponseDTO::new).toList();
    }

    // Sequential Search - Product Search
    public List<ProductResponseDTO> itemSearch(String keyword) {
        List<Product> products = productRepository.findAll();
        List<ProductResponseDTO> results = new ArrayList<>();
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getName().toLowerCase().contains(keyword.toLowerCase())) {
                results.add(new ProductResponseDTO(products.get(i)));
            }
        }
        return results;
    }

    // Method for sorting by ascending price
    public List<ProductResponseDTO> sortByPriceAscending() {
            List<Product> products = productRepository.findAll();
            List<Product> sortedProducts = mergeSortPriceAscending(products);
            return sortedProducts.stream().map(ProductResponseDTO::new).toList();
    }

    // Method for sorting by descending price
    public List<ProductResponseDTO> sortByPriceDescending() {
        List<Product> products = productRepository.findAll();
        List<Product> sortedProducts = mergeSortPriceDescending(products);
        return sortedProducts.stream().map(ProductResponseDTO::new).toList();
    }

    // Method for sorting by availability
    public List<ProductResponseDTO> sortByAvailability() {
        List<Product> products = productRepository.findAll();
        List<Product> sortedProducts = mergeSortAvailability(products);
        return sortedProducts.stream().map(ProductResponseDTO::new).toList();
    }

    // Main method to sort products by priority
    public List<ProductResponseDTO> sortByPriority(double userLat, double userLon) {
        List<Product> products = productRepository.findAll();
        List<Product> sortedProducts = mergeSortPriority(products, userLat, userLon);
        return sortedProducts.stream().map(ProductResponseDTO::new).toList();
    }

    // Method for sorting by proximity to user
    public List<ProductResponseDTO> sortByNearest(double userLat, double userLon) {
        List<Product> products = productRepository.findAll();
        List<Product> sortedProducts = mergeSortProximity(products, userLat, userLon);
        return sortedProducts.stream().map(ProductResponseDTO::new).toList();
    }

    // Helper methods
    // Method to get the lowest price of a product across all branches
    private BigDecimal getLowestPrice(Product product) {
        BigDecimal lowestPrice = null;
        for (ProductVariant variant : product.getProductVariants()) {
            for (BranchProductVariant bpv : variant.getBranchProductVariants()) {
                if (lowestPrice == null || bpv.getPrice().compareTo(lowestPrice) < 0) {
                    lowestPrice = bpv.getPrice();
                }
            }
        }
        return lowestPrice;
    }

    // Helper method to calculate total stock for a product
    private int getTotalStock(Product product) {
    int totalStock = 0;
    for (ProductVariant variant : product.getProductVariants()) {
        for (BranchProductVariant bpv : variant.getBranchProductVariants()) {
            totalStock += bpv.getQuantity();
         }
        }
    return totalStock;
    }

    // Haversine formula to calculate distance between user and branch
    private double calculateDistance(double userLat, double userLon, double branchLat, double branchLon) {
        final int EARTH_RADIUS = 6371;
        double latDistance = Math.toRadians(branchLat - userLat);
        double lonDistance = Math.toRadians(branchLon - userLon);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) + Math.cos(Math.toRadians(userLat)) * Math.cos(Math.toRadians(branchLat)) * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS * c;
    }

    // Method to get the nearest branch distance for a product
    private double getNearestBranchDistance(Product product, double userLat, double userLon) {
        double nearestDistance = Double.MAX_VALUE;
        for (ProductVariant variant : product.getProductVariants()) {
            for (BranchProductVariant bpv : variant.getBranchProductVariants()) {
                double distance = calculateDistance(userLat, userLon, bpv.getBranch().getLatitude(), bpv.getBranch().getLongitude());
                if (distance < nearestDistance) {
                    nearestDistance = distance;
                }
            }
        }

        return nearestDistance;
    }

    // Merge Functions
    // Merge two sorted lists by date
    private List<Product> mergeDate(List<Product> left, List<Product> right) {
    List<Product> merged = new ArrayList<>();
    int i = 0, j = 0;

    while (i < left.size() && j < right.size()) {
        if (left.get(i).getCreatedAt().compareTo(right.get(j).getCreatedAt()) <= 0) {
            merged.add(left.get(i++));
        } else {
            merged.add(right.get(j++));
        }
    }

    merged.addAll(left.subList(i, left.size()));
    merged.addAll(right.subList(j, right.size()));

        return merged;
    }

    // Merge sort for price ascending
    private List<Product> mergePriceAscending(List<Product> left, List<Product> right) {
        List<Product> merged = new ArrayList<>();

        int i = 0;
        int j = 0;

        while (i < left.size() && j < right.size()) {

            if (getLowestPrice(left.get(i)).compareTo(getLowestPrice(right.get(j))) <= 0) {
                merged.add(left.get(i++));
            } else {
                merged.add(right.get(j++));
            }
        }
        merged.addAll(left.subList(i, left.size()));
        merged.addAll(right.subList(j, right.size()));

        return merged;
    }

    // Merge sort for price descending
    private List<Product> mergePriceDescending(List<Product> left, List<Product> right) {
        List<Product> merged = new ArrayList<>();

        int i = 0;
        int j = 0;

        while (i < left.size() && j < right.size()) {

            if (
                getLowestPrice(left.get(i)).compareTo(getLowestPrice(right.get(j))) >= 0) {
                merged.add(left.get(i++));
            } else {
                merged.add(right.get(j++));
            }
        }

        merged.addAll(left.subList(i, left.size()));
        merged.addAll(right.subList(j, right.size()));

        return merged;
    }

    // Merge sort for availability
    private List<Product> mergeAvailability(List<Product> left, List<Product> right) {

        List<Product> merged = new ArrayList<>();

        int i = 0;
        int j = 0;

        while (i < left.size() && j < right.size()) {
            if (getTotalStock(left.get(i)) >= getTotalStock(right.get(j))) {
                merged.add(left.get(i++));
            } else {
                merged.add(right.get(j++));
            }
        }
        merged.addAll(left.subList(i, left.size()));
        merged.addAll(right.subList(j, right.size()));
        return merged;
    }

    // Merge sort for proximity
    private List<Product> mergeProximity(List<Product> left, List<Product> right, double userLat, double userLon) {

        List<Product> merged = new ArrayList<>();

        int i = 0;
        int j = 0;

        while (i < left.size() && j < right.size()) {
            if (getNearestBranchDistance(left.get(i), userLat, userLon) <= getNearestBranchDistance( right.get(j), userLat, userLon)) {
                merged.add(left.get(i++));
            } else {
                merged.add(right.get(j++));
            }
        }
        merged.addAll(left.subList(i, left.size()));
        merged.addAll(right.subList(j, right.size()));
        return merged;
    }

    // Merge sort for priority
    private List<Product> mergePriority(List<Product> left, List<Product> right, double userLat, double userLon) {

        List<Product> merged = new ArrayList<>();

        int i = 0;
        int j = 0;

        while (i < left.size() && j < right.size()) {
            if (comparePriority(left.get(i), right.get(j), userLat, userLon) <= 0) {
                merged.add(left.get(i++));
            } else {

                merged.add(right.get(j++));
            }
        }
        merged.addAll(left.subList(i, left.size()));
        merged.addAll(right.subList(j, right.size()));
        return merged;
    }

    // Recursive merge sort functions
    // Default sorting by date added (newest first)
        private List<Product> defaultSearchPageSort(List<Product> products) {

        if (products.size() <= 1) {
            return products;
        }

        int mid = products.size() / 2;

        List<Product> left =
                defaultSearchPageSort(new ArrayList<>(products.subList(0, mid)));

        List<Product> right =
                defaultSearchPageSort(new ArrayList<>(products.subList(mid, products.size())));

        return mergeDate(left, right);
    }

    // Merge sort for price ascending
    private List<Product> mergeSortPriceAscending(
            List<Product> products) {

        if (products.size() <= 1) {
            return products;
        }

        int mid = products.size() / 2;

        List<Product> left =
                mergeSortPriceAscending(
                        new ArrayList<>(products.subList(0, mid)));

        List<Product> right =
                mergeSortPriceAscending(
                        new ArrayList<>(products.subList(mid, products.size())));

        return mergePriceAscending(left, right);
    }

    // Merge sort for price descending
    private List<Product> mergeSortPriceDescending(
            List<Product> products) {

        if (products.size() <= 1) {
            return products;
        }

        int mid = products.size() / 2;

        List<Product> left =
                mergeSortPriceDescending(
                        new ArrayList<>(products.subList(0, mid)));

        List<Product> right =
                mergeSortPriceDescending(
                        new ArrayList<>(products.subList(mid, products.size())));

        return mergePriceDescending(left, right);
    }

    // Merge sort for availability
    private List<Product> mergeSortAvailability(List<Product> products) {

        if (products.size() <= 1) {
            return products;
        }
        int mid = products.size() / 2;
        List<Product> left = mergeSortAvailability(new ArrayList<>(products.subList(0, mid)));

        List<Product> right = mergeSortAvailability(new ArrayList<>(products.subList(mid, products.size())));
        return mergeAvailability(left, right);
    }

    // Merge sort for proximity
    private List<Product> mergeSortProximity(List<Product> products, double userLat, double userLon) {
        if (products.size() <= 1) {
            return products;
        }

        int mid = products.size() / 2;

        List<Product> left = mergeSortProximity(new ArrayList<>(products.subList(0, mid)), userLat, userLon);

        List<Product> right = mergeSortProximity(new ArrayList<>(products.subList(mid, products.size())), userLat, userLon);

        return mergeProximity(left, right, userLat, userLon);
    }

    private List<Product> mergeSortPriority(List<Product> products, double userLat, double userLon) {

        if (products.size() <= 1) {
            return products;
        }

        int mid = products.size() / 2;

        List<Product> left = mergeSortPriority(new ArrayList<>(products.subList(0, mid)), userLat, userLon);

        List<Product> right = mergeSortPriority(new ArrayList<>(products.subList(mid, products.size())), userLat, userLon);

        return mergePriority(left, right, userLat, userLon);
    }

    // Priority comparator for sorting products based on availability, proximity, and price
    private int comparePriority(Product p1, Product p2, double userLat, double userLon) {

        // Availability
        int stock1 = getTotalStock(p1);
        int stock2 = getTotalStock(p2);

        if (stock1 != stock2) {
            return Integer.compare(stock2, stock1);
        }

        // Proximity
        double distance1 = getNearestBranchDistance(p1, userLat, userLon);

        double distance2 = getNearestBranchDistance(p2, userLat, userLon);

        if (distance1 != distance2) {
            return Double.compare(distance1, distance2);
        }

        // Price
        return getLowestPrice(p1) .compareTo(getLowestPrice(p2));
    }


    // Search and sort by priority
    public List<ProductResponseDTO> searchAndSortPriority(String keyword, double userLat, double userLon) {
    List<Product> products = productRepository.findAll();
    List<Product> matches = new ArrayList<>();
    for (Product product : products) {

        if (product.getName().toLowerCase().contains(keyword.toLowerCase())) {
            matches.add(product);
        }
    }

    List<Product> sorted = mergeSortPriority(matches, userLat, userLon);
    return sorted.stream().map(ProductResponseDTO::new).toList();
    }
}
