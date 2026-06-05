package com.sortcery.backend.seeder;

import com.sortcery.backend.model.Product;
import com.sortcery.backend.model.ProductVariant;
import com.sortcery.backend.repository.ProductRepository;
import com.sortcery.backend.repository.ProductVariantRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.transaction.annotation.Transactional;

@Configuration
public class ProductVariantSeeder {
    @Bean
    @Transactional
    @Order(8)
    CommandLineRunner seedProductVariants(
            ProductVariantRepository productVariantRepository,
            ProductRepository productRepository
    ) {
        return args -> {
            if (productVariantRepository.count() > 0) {
                System.out.println("ProductVariantSeeder: ProductVariants already exist. Skipping seed.");
                return;
            }

            List<ProductVariant> variants = new ArrayList<>();

            // Helper
            BiConsumer<String, String[]> addVariants = (productName, names) -> {
                Product product = productRepository.findByName(productName)
                        .orElseThrow(() -> new RuntimeException("Product not found: " + productName));

                for (String name : names) {
                    variants.add(new ProductVariant(
                            product,
                            name,
                            null
                    ));
                }
            };

            // Coffee & Malt Drinks
            addVariants.accept("Nescafé Classic",
                    new String[]{"25g", "50g", "100g"});

            addVariants.accept("Nescafé 3-in-1 Original",
                    new String[]{"27g Sachet", "10-Pack", "30-Pack"});

            addVariants.accept("Milo Chocolate Malt Drink",
                    new String[]{"22g Sachet", "300g", "1kg"});

            addVariants.accept("Bear Brand Powdered Milk",
                    new String[]{"33g Sachet", "300g", "900g"});

            addVariants.accept("Coffee Mate Original Creamer",
                    new String[]{"50g", "170g", "450g"});

            addVariants.accept("Coffee Mate Brown Sugar Creamer",
                    new String[]{"50g", "170g", "450g"});

            // Milk & Dairy
            addVariants.accept("Alaska Evaporated Milk",
                    new String[]{"140mL", "370mL"});

            addVariants.accept("Alaska Condensada",
                    new String[]{"100g", "300g"});

            addVariants.accept("Anchor Full Cream Milk",
                    new String[]{"33g", "300g", "900g"});

            addVariants.accept("Magnolia Fresh Milk",
                    new String[]{"250mL", "1L"});

            addVariants.accept("Magnolia Butter",
                    new String[]{"100g", "225g"});

            // Beverages
            addVariants.accept("Coca-Cola Original",
                    new String[]{"250mL Can", "500mL Bottle", "1.5L Bottle"});

            addVariants.accept("Coca-Cola Zero",
                    new String[]{"250mL Can", "500mL Bottle", "1.5L Bottle"});

            addVariants.accept("Sprite Lemon-Lime",
                    new String[]{"250mL Can", "500mL Bottle", "1.5L Bottle"});

            addVariants.accept("Royal Orange",
                    new String[]{"250mL Can", "500mL Bottle", "1.5L Bottle"});

            addVariants.accept("Pepsi Cola",
                    new String[]{"250mL Can", "500mL Bottle", "1.5L Bottle"});

            addVariants.accept("Pepsi Max",
                    new String[]{"250mL Can", "500mL Bottle", "1.5L Bottle"});

            addVariants.accept("Mountain Dew Citrus",
                    new String[]{"250mL Can", "500mL Bottle", "1.5L Bottle"});

            addVariants.accept("Gatorade Blue Bolt",
                    new String[]{"350mL", "500mL"});

            addVariants.accept("Gatorade Orange",
                    new String[]{"350mL", "500mL"});

            // Instant Noodles
            addVariants.accept("Lucky Me Pancit Canton Original",
                    new String[]{"60g", "120g"});

            addVariants.accept("Lucky Me Pancit Canton Kalamansi",
                    new String[]{"60g", "120g"});

            addVariants.accept("Lucky Me Beef Instant Mami",
                    new String[]{"55g", "70g"});

            addVariants.accept("Payless Instant Mami Beef",
                    new String[]{"65g", "130g"});

            addVariants.accept("Payless Xtra Big Pancit Canton",
                    new String[]{"130g"});

            // Canned Goods
            addVariants.accept("Del Monte Corned Tuna",
                    new String[]{"155g", "180g"});

            addVariants.accept("Del Monte Pineapple Chunks",
                    new String[]{"227g", "565g"});

            addVariants.accept("Del Monte Fruit Cocktail",
                    new String[]{"250g", "836g"});

            // Condiments
            addVariants.accept("UFC Banana Ketchup",
                    new String[]{"320g", "550g", "1kg"});

            addVariants.accept("UFC Tomato Ketchup",
                    new String[]{"320g", "550g", "1kg"});

            addVariants.accept("UFC Soy Sauce",
                    new String[]{"350mL", "1L"});

            // Snacks
            addVariants.accept("Oishi Prawn Crackers",
                    new String[]{"24g", "50g", "90g"});

            addVariants.accept("Oishi Ribbed Cracklings",
                    new String[]{"24g", "50g", "90g"});

            addVariants.accept("Piattos Cheese",
                    new String[]{"40g", "85g", "130g"});

            addVariants.accept("Piattos Sour Cream",
                    new String[]{"40g", "85g", "130g"});

            addVariants.accept("Nova Multigrain Chips",
                    new String[]{"40g", "78g"});

            addVariants.accept("V-Cut Potato Chips",
                    new String[]{"25g", "60g", "110g"});

            addVariants.accept("Rinbee Sweet Corn Snack",
                    new String[]{"20g", "50g"});

            addVariants.accept("Pillows Chocolate Filled Snack",
                    new String[]{"38g", "85g"});

            // Crackers
            addVariants.accept("SkyFlakes Crackers",
                    new String[]{"25g", "50g", "100g"});

            addVariants.accept("Fita Crackers",
                    new String[]{"30g", "100g"});

            // Personal Care
            addVariants.accept("Safeguard Original Soap",
                    new String[]{"60g", "130g"});

            addVariants.accept("Safeguard Lemon Fresh Soap",
                    new String[]{"60g", "130g"});

            addVariants.accept("Dove Beauty Bar Original",
                    new String[]{"90g", "135g"});

            addVariants.accept("Dove Body Wash Deep Moisture",
                    new String[]{"180mL", "400mL"});

            // Hair Care
            addVariants.accept("Palmolive Shampoo Anti-Dandruff",
                    new String[]{"20mL Sachet", "180mL", "350mL"});

            addVariants.accept("Cream Silk Conditioner Damage Control",
                    new String[]{"20mL Sachet", "180mL", "350mL"});

            // Oral Care
            addVariants.accept("Colgate Triple Action Toothpaste",
                    new String[]{"40g", "100g", "150g"});

            addVariants.accept("Closeup Ever Fresh Toothpaste",
                    new String[]{"40g", "100g", "150g"});

            // Laundry
            addVariants.accept("Tide Powder Detergent",
                    new String[]{"70g", "500g", "1kg"});

            addVariants.accept("Surf Powder Detergent",
                    new String[]{"70g", "500g", "1kg"});

            addVariants.accept("Ariel Powder Detergent",
                    new String[]{"70g", "500g", "1kg"});

            // Dishwashing
            addVariants.accept("Joy Dishwashing Liquid Original",
                    new String[]{"100mL", "250mL", "495mL"});

            // Household Cleaning
            addVariants.accept("Zonrox Bleach Original",
                    new String[]{"250mL", "500mL", "1L"});

            productVariantRepository.saveAll(variants);

            System.out.println(
                    "ProductVariantSeeder: Seeded " + variants.size() + " variants."
            );
        };
    }
}
