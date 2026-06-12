package com.sortcery.backend.seeder;

import com.sortcery.backend.model.Brand;
import com.sortcery.backend.model.Product;
import com.sortcery.backend.model.ProductCategory;
import com.sortcery.backend.repository.BrandRepository;
import com.sortcery.backend.repository.ProductCategoryRepository;
import com.sortcery.backend.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.transaction.annotation.Transactional;

@Configuration
public class ProductSeeder {
    @Bean
    @Transactional
    @Order(7)
    CommandLineRunner seedProducts(
        ProductRepository productRepository,
        BrandRepository brandRepository,
        ProductCategoryRepository productCategoryRepository
    ) {
        return args -> {
            if (productRepository.count() > 0) {
                System.out.println("ProductSeeder: Products already exist. Skipping seed.");
                return;
            }

            Map<String, Brand> brands = brandRepository.findAll()
                .stream()
                .collect(Collectors.toMap(Brand::getName, b -> b));

            Map<String, ProductCategory> categories = productCategoryRepository.findAll()
                .stream()
                .collect(Collectors.toMap(ProductCategory::getName, c -> c));

            List<Product> products = new ArrayList<>(); 

            products.add(new Product(categories.get("Coffee & Malt Drinks"), brands.get("Nescafé"), "Nescafé Classic", null));
            products.add(new Product(categories.get("Coffee & Malt Drinks"), brands.get("Nescafé"), "Nescafé 3-in-1 Original", null));

            products.add(new Product(categories.get("Coffee & Malt Drinks"), brands.get("Milo"), "Milo Chocolate Malt Drink", null));

            products.add(new Product(categories.get("Milk & Dairy"), brands.get("Bear Brand"), "Bear Brand Powdered Milk", null));

            products.add(new Product(categories.get("Coffee & Malt Drinks"), brands.get("Coffee Mate"), "Coffee Mate Original Creamer", null));
            products.add(new Product(categories.get("Coffee & Malt Drinks"), brands.get("Coffee Mate"), "Coffee Mate Brown Sugar Creamer", null));

            products.add(new Product(categories.get("Milk & Dairy"), brands.get("Alaska"), "Alaska Evaporated Milk", null));
            products.add(new Product(categories.get("Milk & Dairy"), brands.get("Alaska"), "Alaska Condensada", null));

            products.add(new Product(categories.get("Milk & Dairy"), brands.get("Anchor"), "Anchor Full Cream Milk", null));

            products.add(new Product(categories.get("Milk & Dairy"), brands.get("Magnolia"), "Magnolia Fresh Milk", null));
            products.add(new Product(categories.get("Milk & Dairy"), brands.get("Magnolia"), "Magnolia Butter", null));

            products.add(new Product(categories.get("Beverages"), brands.get("Coca-Cola"), "Coca-Cola Original", null));
            products.add(new Product(categories.get("Beverages"), brands.get("Coca-Cola"), "Coca-Cola Zero", null));

            products.add(new Product(categories.get("Beverages"), brands.get("Sprite"), "Sprite Lemon-Lime", null));
            products.add(new Product(categories.get("Beverages"), brands.get("Royal"), "Royal Orange", null));

            products.add(new Product(categories.get("Beverages"), brands.get("Pepsi"), "Pepsi Cola", null));
            products.add(new Product(categories.get("Beverages"), brands.get("Pepsi"), "Pepsi Max", null));

            products.add(new Product(categories.get("Beverages"), brands.get("Mountain Dew"), "Mountain Dew Citrus", null));

            products.add(new Product(categories.get("Beverages"), brands.get("Gatorade"), "Gatorade Blue Bolt", null));
            products.add(new Product(categories.get("Beverages"), brands.get("Gatorade"), "Gatorade Orange", null));

            products.add(new Product(categories.get("Instant Noodles"), brands.get("Lucky Me"), "Lucky Me Pancit Canton Original", null));
            products.add(new Product(categories.get("Instant Noodles"), brands.get("Lucky Me"), "Lucky Me Pancit Canton Kalamansi", null));
            products.add(new Product(categories.get("Instant Noodles"), brands.get("Lucky Me"), "Lucky Me Beef Instant Mami", null));

            products.add(new Product(categories.get("Instant Noodles"), brands.get("Payless"), "Payless Instant Mami Beef", null));
            products.add(new Product(categories.get("Instant Noodles"), brands.get("Payless"), "Payless Xtra Big Pancit Canton", null));

            products.add(new Product(categories.get("Canned Goods"), brands.get("Del Monte"), "Del Monte Corned Tuna", null));
            products.add(new Product(categories.get("Canned Goods"), brands.get("Del Monte"), "Del Monte Pineapple Chunks", null));
            products.add(new Product(categories.get("Canned Goods"), brands.get("Del Monte"), "Del Monte Fruit Cocktail", null));

            products.add(new Product(categories.get("Condiments & Sauces"), brands.get("UFC"), "UFC Banana Ketchup", null));
            products.add(new Product(categories.get("Condiments & Sauces"), brands.get("UFC"), "UFC Tomato Ketchup", null));
            products.add(new Product(categories.get("Condiments & Sauces"), brands.get("UFC"), "UFC Soy Sauce", null));

            products.add(new Product(categories.get("Snacks"), brands.get("Oishi"), "Oishi Prawn Crackers", null));
            products.add(new Product(categories.get("Snacks"), brands.get("Oishi"), "Oishi Ribbed Cracklings", null));

            products.add(new Product(categories.get("Snacks"), brands.get("Piattos"), "Piattos Cheese", null));
            products.add(new Product(categories.get("Snacks"), brands.get("Piattos"), "Piattos Sour Cream", null));

            products.add(new Product(categories.get("Snacks"), brands.get("Nova"), "Nova Multigrain Chips", null));
            products.add(new Product(categories.get("Snacks"), brands.get("V-Cut"), "V-Cut Potato Chips", null));

            products.add(new Product(categories.get("Snacks"), brands.get("Rinbee"), "Rinbee Sweet Corn Snack", null));
            products.add(new Product(categories.get("Snacks"), brands.get("Pillows"), "Pillows Chocolate Filled Snack", null));

            products.add(new Product(categories.get("Biscuits & Crackers"), brands.get("SkyFlakes"), "SkyFlakes Crackers", null));
            products.add(new Product(categories.get("Biscuits & Crackers"), brands.get("Fita"), "Fita Crackers", null));

            products.add(new Product(categories.get("Personal Care"), brands.get("Safeguard"), "Safeguard Original Soap", null));
            products.add(new Product(categories.get("Personal Care"), brands.get("Safeguard"), "Safeguard Lemon Fresh Soap", null));

            products.add(new Product(categories.get("Personal Care"), brands.get("Dove"), "Dove Beauty Bar Original", null));
            products.add(new Product(categories.get("Personal Care"), brands.get("Dove"), "Dove Body Wash Deep Moisture", null));

            products.add(new Product(categories.get("Hair Care"), brands.get("Palmolive"), "Palmolive Shampoo Anti-Dandruff", null));
            products.add(new Product(categories.get("Hair Care"), brands.get("Cream Silk"), "Cream Silk Conditioner Damage Control", null));

            products.add(new Product(categories.get("Oral Care"), brands.get("Colgate"), "Colgate Triple Action Toothpaste", null));
            products.add(new Product(categories.get("Oral Care"), brands.get("Closeup"), "Closeup Ever Fresh Toothpaste", null));

            products.add(new Product(categories.get("Laundry"), brands.get("Tide"), "Tide Powder Detergent", null));
            products.add(new Product(categories.get("Laundry"), brands.get("Surf"), "Surf Powder Detergent", null));
            products.add(new Product(categories.get("Laundry"), brands.get("Ariel"), "Ariel Powder Detergent", null));

            products.add(new Product(categories.get("Dishwashing"), brands.get("Joy"), "Joy Dishwashing Liquid Original", null));

            products.add(new Product(categories.get("Household Cleaning"), brands.get("Zonrox"), "Zonrox Bleach Original", null));

            productRepository.saveAll(products);
            System.out.println("ProductSeeder: Seeded " + products.size() + " products.");
        };
    }
}
