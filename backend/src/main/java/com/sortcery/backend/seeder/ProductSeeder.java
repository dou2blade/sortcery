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

            products.add(new Product(categories.get("Coffee & Malt Drinks"), brands.get("Nescafé"), "Nescafé Classic", "https://placehold.co/600x400?font=lora&text=Nescafé Classic"));
            products.add(new Product(categories.get("Coffee & Malt Drinks"), brands.get("Nescafé"), "Nescafé 3-in-1 Original", "https://placehold.co/600x400?font=lora&text=Nescafé 3-in-1 Original"));

            products.add(new Product(categories.get("Coffee & Malt Drinks"), brands.get("Milo"), "Milo Chocolate Malt Drink", "https://placehold.co/600x400?font=lora&text=Milo Chocolate Malt Drink"));

            products.add(new Product(categories.get("Milk & Dairy"), brands.get("Bear Brand"), "Bear Brand Powdered Milk", "https://placehold.co/600x400?font=lora&text=Bear Brand Powdered Milk"));

            products.add(new Product(categories.get("Coffee & Malt Drinks"), brands.get("Coffee Mate"), "Coffee Mate Original Creamer", "https://placehold.co/600x400?font=lora&text=Coffee Mate Original Creamer"));
            products.add(new Product(categories.get("Coffee & Malt Drinks"), brands.get("Coffee Mate"), "Coffee Mate Brown Sugar Creamer", "https://placehold.co/600x400?font=lora&text=Coffee Mate Brown Sugar Creamer"));

            products.add(new Product(categories.get("Milk & Dairy"), brands.get("Alaska"), "Alaska Evaporated Milk", "https://placehold.co/600x400?font=lora&text=Alaska Evaporated Milk"));
            products.add(new Product(categories.get("Milk & Dairy"), brands.get("Alaska"), "Alaska Condensada", "https://placehold.co/600x400?font=lora&text=Alaska Condensada"));

            products.add(new Product(categories.get("Milk & Dairy"), brands.get("Anchor"), "Anchor Full Cream Milk", "https://placehold.co/600x400?font=lora&text=Anchor Full Cream Milk"));

            products.add(new Product(categories.get("Milk & Dairy"), brands.get("Magnolia"), "Magnolia Fresh Milk", "https://placehold.co/600x400?font=lora&text=Magnolia Fresh Milk"));
            products.add(new Product(categories.get("Milk & Dairy"), brands.get("Magnolia"), "Magnolia Butter", "https://placehold.co/600x400?font=lora&text=Magnolia Butter"));

            products.add(new Product(categories.get("Beverages"), brands.get("Coca-Cola"), "Coca-Cola Original", "https://placehold.co/600x400?font=lora&text=Coca-Cola Original"));
            products.add(new Product(categories.get("Beverages"), brands.get("Coca-Cola"), "Coca-Cola Zero", "https://placehold.co/600x400?font=lora&text=Coca-Cola Zero"));

            products.add(new Product(categories.get("Beverages"), brands.get("Sprite"), "Sprite Lemon-Lime", "https://placehold.co/600x400?font=lora&text=Sprite Lemon-Lime"));
            products.add(new Product(categories.get("Beverages"), brands.get("Royal"), "Royal Orange", "https://placehold.co/600x400?font=lora&text=Royal Orange"));

            products.add(new Product(categories.get("Beverages"), brands.get("Pepsi"), "Pepsi Cola", "https://placehold.co/600x400?font=lora&text=Pepsi Cola"));
            products.add(new Product(categories.get("Beverages"), brands.get("Pepsi"), "Pepsi Max", "https://placehold.co/600x400?font=lora&text=Pepsi Max"));

            products.add(new Product(categories.get("Beverages"), brands.get("Mountain Dew"), "Mountain Dew Citrus", "https://placehold.co/600x400?font=lora&text=Mountain Dew Citrus"));

            products.add(new Product(categories.get("Beverages"), brands.get("Gatorade"), "Gatorade Blue Bolt", "https://placehold.co/600x400?font=lora&text=Gatorade Blue Bolt"));
            products.add(new Product(categories.get("Beverages"), brands.get("Gatorade"), "Gatorade Orange", "https://placehold.co/600x400?font=lora&text=Gatorade Orange"));

            products.add(new Product(categories.get("Instant Noodles"), brands.get("Lucky Me"), "Lucky Me Pancit Canton Original", "https://placehold.co/600x400?font=lora&text=Lucky Me Pancit Canton Original"));
            products.add(new Product(categories.get("Instant Noodles"), brands.get("Lucky Me"), "Lucky Me Pancit Canton Kalamansi", "https://placehold.co/600x400?font=lora&text=Lucky Me Pancit Canton Kalamansi"));
            products.add(new Product(categories.get("Instant Noodles"), brands.get("Lucky Me"), "Lucky Me Beef Instant Mami", "https://placehold.co/600x400?font=lora&text=Lucky Me Beef Instant Mami"));

            products.add(new Product(categories.get("Instant Noodles"), brands.get("Payless"), "Payless Instant Mami Beef", "https://placehold.co/600x400?font=lora&text=Payless Instant Mami Beef"));
            products.add(new Product(categories.get("Instant Noodles"), brands.get("Payless"), "Payless Xtra Big Pancit Canton", "https://placehold.co/600x400?font=lora&text=Payless Xtra Big Pancit Canton"));

            products.add(new Product(categories.get("Canned Goods"), brands.get("Del Monte"), "Del Monte Corned Tuna", "https://placehold.co/600x400?font=lora&text=Del Monte Corned Tuna"));
            products.add(new Product(categories.get("Canned Goods"), brands.get("Del Monte"), "Del Monte Pineapple Chunks", "https://placehold.co/600x400?font=lora&text=Del Monte Pineapple Chunks"));
            products.add(new Product(categories.get("Canned Goods"), brands.get("Del Monte"), "Del Monte Fruit Cocktail", "https://placehold.co/600x400?font=lora&text=Del Monte Fruit Cocktail"));

            products.add(new Product(categories.get("Condiments & Sauces"), brands.get("UFC"), "UFC Banana Ketchup", "https://placehold.co/600x400?font=lora&text=UFC Banana Ketchup"));
            products.add(new Product(categories.get("Condiments & Sauces"), brands.get("UFC"), "UFC Tomato Ketchup", "https://placehold.co/600x400?font=lora&text=UFC Tomato Ketchup"));
            products.add(new Product(categories.get("Condiments & Sauces"), brands.get("UFC"), "UFC Soy Sauce", "https://placehold.co/600x400?font=lora&text=UFC Soy Sauce"));

            products.add(new Product(categories.get("Snacks"), brands.get("Oishi"), "Oishi Prawn Crackers", "https://placehold.co/600x400?font=lora&text=Oishi Prawn Crackers"));
            products.add(new Product(categories.get("Snacks"), brands.get("Oishi"), "Oishi Ribbed Cracklings", "https://placehold.co/600x400?font=lora&text=Oishi Ribbed Cracklings"));

            products.add(new Product(categories.get("Snacks"), brands.get("Piattos"), "Piattos Cheese", "https://placehold.co/600x400?font=lora&text=Piattos Cheese"));
            products.add(new Product(categories.get("Snacks"), brands.get("Piattos"), "Piattos Sour Cream", "https://placehold.co/600x400?font=lora&text=Piattos Sour Cream"));

            products.add(new Product(categories.get("Snacks"), brands.get("Nova"), "Nova Multigrain Chips", "https://placehold.co/600x400?font=lora&text=Nova Multigrain Chips"));
            products.add(new Product(categories.get("Snacks"), brands.get("V-Cut"), "V-Cut Potato Chips", "https://placehold.co/600x400?font=lora&text=V-Cut Potato Chips"));

            products.add(new Product(categories.get("Snacks"), brands.get("Rinbee"), "Rinbee Sweet Corn Snack", "https://placehold.co/600x400?font=lora&text=Rinbee Sweet Corn Snack"));
            products.add(new Product(categories.get("Snacks"), brands.get("Pillows"), "Pillows Chocolate Filled Snack", "https://placehold.co/600x400?font=lora&text=Pillows Chocolate Filled Snack"));

            products.add(new Product(categories.get("Biscuits & Crackers"), brands.get("SkyFlakes"), "SkyFlakes Crackers", "https://placehold.co/600x400?font=lora&text=SkyFlakes Crackers"));
            products.add(new Product(categories.get("Biscuits & Crackers"), brands.get("Fita"), "Fita Crackers", "https://placehold.co/600x400?font=lora&text=Fita Crackers"));

            products.add(new Product(categories.get("Personal Care"), brands.get("Safeguard"), "Safeguard Original Soap", "https://placehold.co/600x400?font=lora&text=Safeguard Original Soap"));
            products.add(new Product(categories.get("Personal Care"), brands.get("Safeguard"), "Safeguard Lemon Fresh Soap", "https://placehold.co/600x400?font=lora&text=Safeguard Lemon Fresh Soap"));

            products.add(new Product(categories.get("Personal Care"), brands.get("Dove"), "Dove Beauty Bar Original", "https://placehold.co/600x400?font=lora&text=Dove Beauty Bar Original"));
            products.add(new Product(categories.get("Personal Care"), brands.get("Dove"), "Dove Body Wash Deep Moisture", "https://placehold.co/600x400?font=lora&text=Dove Body Wash Deep Moisture"));

            products.add(new Product(categories.get("Hair Care"), brands.get("Palmolive"), "Palmolive Shampoo Anti-Dandruff", "https://placehold.co/600x400?font=lora&text=Palmolive Shampoo Anti-Dandruff"));
            products.add(new Product(categories.get("Hair Care"), brands.get("Cream Silk"), "Cream Silk Conditioner Damage Control", "https://placehold.co/600x400?font=lora&text=Cream Silk Conditioner Damage Control"));

            products.add(new Product(categories.get("Oral Care"), brands.get("Colgate"), "Colgate Triple Action Toothpaste", "https://placehold.co/600x400?font=lora&text=Colgate Triple Action Toothpaste"));
            products.add(new Product(categories.get("Oral Care"), brands.get("Closeup"), "Closeup Ever Fresh Toothpaste", "https://placehold.co/600x400?font=lora&text=Closeup Ever Fresh Toothpaste"));

            products.add(new Product(categories.get("Laundry"), brands.get("Tide"), "Tide Powder Detergent", "https://placehold.co/600x400?font=lora&text=Tide Powder Detergent"));
            products.add(new Product(categories.get("Laundry"), brands.get("Surf"), "Surf Powder Detergent", "https://placehold.co/600x400?font=lora&text=Surf Powder Detergent"));
            products.add(new Product(categories.get("Laundry"), brands.get("Ariel"), "Ariel Powder Detergent", "https://placehold.co/600x400?font=lora&text=Ariel Powder Detergent"));

            products.add(new Product(categories.get("Dishwashing"), brands.get("Joy"), "Joy Dishwashing Liquid Original", "https://placehold.co/600x400?font=lora&text=Joy Dishwashing Liquid Original"));

            products.add(new Product(categories.get("Household Cleaning"), brands.get("Zonrox"), "Zonrox Bleach Original", "https://placehold.co/600x400?font=lora&text=Zonrox Bleach Original"));

            productRepository.saveAll(products);
            System.out.println("ProductSeeder: Seeded " + products.size() + " products.");
        };
    }
}
