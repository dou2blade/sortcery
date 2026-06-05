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

            products.add(new Product(categories.get("Coffee & Malt Drinks"), brands.get("Nescafé"), "Nescafé Classic"));
            products.add(new Product(categories.get("Coffee & Malt Drinks"), brands.get("Nescafé"), "Nescafé 3-in-1 Original"));

            products.add(new Product(categories.get("Coffee & Malt Drinks"), brands.get("Milo"), "Milo Chocolate Malt Drink"));

            products.add(new Product(categories.get("Milk & Dairy"), brands.get("Bear Brand"), "Bear Brand Powdered Milk"));

            products.add(new Product(categories.get("Coffee & Malt Drinks"), brands.get("Coffee Mate"), "Coffee Mate Original Creamer"));
            products.add(new Product(categories.get("Coffee & Malt Drinks"), brands.get("Coffee Mate"), "Coffee Mate Brown Sugar Creamer"));

            products.add(new Product(categories.get("Milk & Dairy"), brands.get("Alaska"), "Alaska Evaporated Milk"));
            products.add(new Product(categories.get("Milk & Dairy"), brands.get("Alaska"), "Alaska Condensada"));

            products.add(new Product(categories.get("Milk & Dairy"), brands.get("Anchor"), "Anchor Full Cream Milk"));

            products.add(new Product(categories.get("Milk & Dairy"), brands.get("Magnolia"), "Magnolia Fresh Milk"));
            products.add(new Product(categories.get("Milk & Dairy"), brands.get("Magnolia"), "Magnolia Butter"));

            products.add(new Product(categories.get("Beverages"), brands.get("Coca-Cola"), "Coca-Cola Original"));
            products.add(new Product(categories.get("Beverages"), brands.get("Coca-Cola"), "Coca-Cola Zero"));

            products.add(new Product(categories.get("Beverages"), brands.get("Sprite"), "Sprite Lemon-Lime"));
            products.add(new Product(categories.get("Beverages"), brands.get("Royal"), "Royal Orange"));

            products.add(new Product(categories.get("Beverages"), brands.get("Pepsi"), "Pepsi Cola"));
            products.add(new Product(categories.get("Beverages"), brands.get("Pepsi"), "Pepsi Max"));

            products.add(new Product(categories.get("Beverages"), brands.get("Mountain Dew"), "Mountain Dew Citrus"));

            products.add(new Product(categories.get("Beverages"), brands.get("Gatorade"), "Gatorade Blue Bolt"));
            products.add(new Product(categories.get("Beverages"), brands.get("Gatorade"), "Gatorade Orange"));

            products.add(new Product(categories.get("Instant Noodles"), brands.get("Lucky Me"), "Lucky Me Pancit Canton Original"));
            products.add(new Product(categories.get("Instant Noodles"), brands.get("Lucky Me"), "Lucky Me Pancit Canton Kalamansi"));
            products.add(new Product(categories.get("Instant Noodles"), brands.get("Lucky Me"), "Lucky Me Beef Instant Mami"));

            products.add(new Product(categories.get("Instant Noodles"), brands.get("Payless"), "Payless Instant Mami Beef"));
            products.add(new Product(categories.get("Instant Noodles"), brands.get("Payless"), "Payless Xtra Big Pancit Canton"));

            products.add(new Product(categories.get("Canned Goods"), brands.get("Del Monte"), "Del Monte Corned Tuna"));
            products.add(new Product(categories.get("Canned Goods"), brands.get("Del Monte"), "Del Monte Pineapple Chunks"));
            products.add(new Product(categories.get("Canned Goods"), brands.get("Del Monte"), "Del Monte Fruit Cocktail"));

            products.add(new Product(categories.get("Condiments & Sauces"), brands.get("UFC"), "UFC Banana Ketchup"));
            products.add(new Product(categories.get("Condiments & Sauces"), brands.get("UFC"), "UFC Tomato Ketchup"));
            products.add(new Product(categories.get("Condiments & Sauces"), brands.get("UFC"), "UFC Soy Sauce"));

            products.add(new Product(categories.get("Snacks"), brands.get("Oishi"), "Oishi Prawn Crackers"));
            products.add(new Product(categories.get("Snacks"), brands.get("Oishi"), "Oishi Ribbed Cracklings"));

            products.add(new Product(categories.get("Snacks"), brands.get("Piattos"), "Piattos Cheese"));
            products.add(new Product(categories.get("Snacks"), brands.get("Piattos"), "Piattos Sour Cream"));

            products.add(new Product(categories.get("Snacks"), brands.get("Nova"), "Nova Multigrain Chips"));
            products.add(new Product(categories.get("Snacks"), brands.get("V-Cut"), "V-Cut Potato Chips"));

            products.add(new Product(categories.get("Snacks"), brands.get("Rinbee"), "Rinbee Sweet Corn Snack"));
            products.add(new Product(categories.get("Snacks"), brands.get("Pillows"), "Pillows Chocolate Filled Snack"));

            products.add(new Product(categories.get("Biscuits & Crackers"), brands.get("SkyFlakes"), "SkyFlakes Crackers"));
            products.add(new Product(categories.get("Biscuits & Crackers"), brands.get("Fita"), "Fita Crackers"));

            products.add(new Product(categories.get("Personal Care"), brands.get("Safeguard"), "Safeguard Original Soap"));
            products.add(new Product(categories.get("Personal Care"), brands.get("Safeguard"), "Safeguard Lemon Fresh Soap"));

            products.add(new Product(categories.get("Personal Care"), brands.get("Dove"), "Dove Beauty Bar Original"));
            products.add(new Product(categories.get("Personal Care"), brands.get("Dove"), "Dove Body Wash Deep Moisture"));

            products.add(new Product(categories.get("Hair Care"), brands.get("Palmolive"), "Palmolive Shampoo Anti-Dandruff"));
            products.add(new Product(categories.get("Hair Care"), brands.get("Cream Silk"), "Cream Silk Conditioner Damage Control"));

            products.add(new Product(categories.get("Oral Care"), brands.get("Colgate"), "Colgate Triple Action Toothpaste"));
            products.add(new Product(categories.get("Oral Care"), brands.get("Closeup"), "Closeup Ever Fresh Toothpaste"));

            products.add(new Product(categories.get("Laundry"), brands.get("Tide"), "Tide Powder Detergent"));
            products.add(new Product(categories.get("Laundry"), brands.get("Surf"), "Surf Powder Detergent"));
            products.add(new Product(categories.get("Laundry"), brands.get("Ariel"), "Ariel Powder Detergent"));

            products.add(new Product(categories.get("Dishwashing"), brands.get("Joy"), "Joy Dishwashing Liquid Original"));

            products.add(new Product(categories.get("Household Cleaning"), brands.get("Zonrox"), "Zonrox Bleach Original"));

            productRepository.saveAll(products);
            System.out.println("ProductSeeder: Seeded " + products.size() + " products.");
        };
    }
}
