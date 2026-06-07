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
    private final ProductRepository productRepository;

    public UserSortingService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductResponseDTO> defaultSorting() {
        List<Product> products = productRepository.findAll();
        List<Product> sortedProducts = defaultSearchPageSort(products);
        return sortedProducts.stream()
            .map(ProductResponseDTO::new)
            .toList();
    }

    private int getTotalStock(Product product) {
    int totalStock = 0;
    for (ProductVariant variant : product.getProductVariants()) {
        for (BranchProductVariant bpv :
                variant.getBranchProductVariants()) {
            totalStock += bpv.getQuantity();
        }
    }



    return totalStock;
}

    public List<ProductResponseDTO> sortByAvailability() {

    List<Product> products = productRepository.findAll();

    List<Product> sortedProducts =
            mergeSortAvailability(products);

    return sortedProducts.stream()
            .map(ProductResponseDTO::new)
            .toList();
}

        public List<ProductResponseDTO> sortByPriceAscending() {
            List<Product> products = productRepository.findAll();
            List<Product> sortedProducts = mergeSortPriceAscending(products);
            
            return sortedProducts.stream()
                .map(ProductResponseDTO::new)
                .toList();
    }

    public List<ProductResponseDTO> sortByPriceDescending() {
        List<Product> products = productRepository.findAll();
        List<Product> sortedProducts = mergeSortPriceDescending(products);
        return sortedProducts.stream()
            .map(ProductResponseDTO::new)
            .toList();
}

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

    // Merge Sort - Default Sorting of Products (by date) before applying any filters
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

private List<Product> mergePriceAscending(
        List<Product> left,
        List<Product> right) {

    List<Product> merged = new ArrayList<>();

    int i = 0;
    int j = 0;

    while (i < left.size() && j < right.size()) {

        if (
            getLowestPrice(left.get(i))
                .compareTo(
                    getLowestPrice(right.get(j))
                ) <= 0
        ) {

            merged.add(left.get(i++));
        } else {

            merged.add(right.get(j++));
        }
    }

    merged.addAll(left.subList(i, left.size()));
    merged.addAll(right.subList(j, right.size()));

    return merged;
}

private List<Product> mergePriceDescending(
        List<Product> left,
        List<Product> right) {

    List<Product> merged = new ArrayList<>();

    int i = 0;
    int j = 0;

    while (i < left.size() && j < right.size()) {

        if (
            getLowestPrice(left.get(i))
                .compareTo(
                    getLowestPrice(right.get(j))
                ) >= 0
        ) {

            merged.add(left.get(i++));
        } else {

            merged.add(right.get(j++));
        }
    }

    merged.addAll(left.subList(i, left.size()));
    merged.addAll(right.subList(j, right.size()));

    return merged;
}

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

    private BigDecimal getLowestPrice(Product product) {

    BigDecimal lowestPrice = BigDecimal.valueOf(Double.MAX_VALUE);

    for (ProductVariant variant : product.getProductVariants()) {

        for (BranchProductVariant bpv :
                variant.getBranchProductVariants()) {

            if (lowestPrice == null
                    || bpv.getPrice().compareTo(lowestPrice) < 0) {

                lowestPrice = bpv.getPrice();
            }
        }
    }

    return lowestPrice;
}

private List<Product> mergeAvailability(
        List<Product> left,
        List<Product> right) {

    List<Product> merged = new ArrayList<>();

    int i = 0;
    int j = 0;

    while (i < left.size() && j < right.size()) {

        if (
            getTotalStock(left.get(i))
                >=
            getTotalStock(right.get(j))
        ) {

            merged.add(left.get(i++));
        } else {

            merged.add(right.get(j++));
        }
    }

    merged.addAll(left.subList(i, left.size()));
    merged.addAll(right.subList(j, right.size()));

    return merged;
}

private List<Product> mergeSortAvailability(
        List<Product> products) {

    if (products.size() <= 1) {
        return products;
    }

    int mid = products.size() / 2;

    List<Product> left =
            mergeSortAvailability(
                    new ArrayList<>(products.subList(0, mid)));

    List<Product> right =
            mergeSortAvailability(
                    new ArrayList<>(products.subList(mid, products.size())));

    return mergeAvailability(left, right);
}

}
