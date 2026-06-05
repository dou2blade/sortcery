package com.sortcery.backend.seeder;

import com.sortcery.backend.model.ProductCategory;
import com.sortcery.backend.repository.ProductCategoryRepository;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.transaction.annotation.Transactional;

@Configuration
public class ProductCategorySeeder {
    @Bean
    @Transactional
    @Order(6)
    CommandLineRunner seedProductCategories(ProductCategoryRepository productCategoryRepository) {
        return args -> {
            if (productCategoryRepository.count() > 0) {
                System.out.println("ProductCategorySeeder: ProductCategories already exist. Skipping seed.");
                return;
            }

            List<String> productCategoryNames = List.of(
                "Beverages",
                "Coffee & Malt Drinks",
                "Milk & Dairy",

                "Instant Noodles",
                "Snacks",
                "Biscuits & Crackers",

                "Canned Goods",
                "Condiments & Sauces",

                "Personal Care",
                "Hair Care",
                "Oral Care",

                "Laundry",
                "Dishwashing",
                "Household Cleaning"
            );

            List<ProductCategory> productCategories = productCategoryNames.stream()
                .map(ProductCategory::new)
                .toList();

            productCategoryRepository.saveAll(productCategories);
            System.out.println("ProductCategorySeeder: Seeded " + productCategories.size() + " productCategories.");
        };
    }
}
