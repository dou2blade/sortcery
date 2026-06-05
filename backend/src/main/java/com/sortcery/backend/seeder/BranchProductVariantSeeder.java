package com.sortcery.backend.seeder;

import com.sortcery.backend.model.Branch;
import com.sortcery.backend.model.BranchProductVariant;
import com.sortcery.backend.model.ProductVariant;
import com.sortcery.backend.repository.BranchProductVariantRepository;
import com.sortcery.backend.repository.BranchRepository;
import com.sortcery.backend.repository.ProductVariantRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.transaction.annotation.Transactional;

@Configuration
public class BranchProductVariantSeeder {
    @Bean
    @Transactional
    @Order(9)
    CommandLineRunner seedBranchProductVariants(
        BranchProductVariantRepository branchProductVariantRepository,
        BranchRepository branchRepository,
        ProductVariantRepository productVariantRepository
    ) {
        return args -> {
            if (branchProductVariantRepository.count() > 0) {
                System.out.println(
                    "BranchProductVariantSeeder: Already seeded. Skipping."
                );
                return;
            }

            Random random = new Random();

            List<Branch> branches = branchRepository.findAll();
            List<ProductVariant> variants = productVariantRepository.findAll();

            List<BranchProductVariant> records = new ArrayList<>();

            for (Branch branch : branches) {

                double storeMultiplier =
                    switch (branch.getStore().getName()) {
                        case "ValueHub Stores" -> 0.97;
                        case "CommunityGrocer" -> 0.99;
                        case "FreshMart" -> 1.00;
                        case "PrimeChoice Market" -> 1.04;
                        case "UrbanBasket" -> 1.08;
                        default -> 1.00;
                    };

                for (ProductVariant variant : variants) {

                    BigDecimal basePrice = determineBasePrice(variant);

                    double branchVariance =
                        0.97 + (random.nextDouble() * 0.06);

                    BigDecimal finalPrice = basePrice
                        .multiply(BigDecimal.valueOf(storeMultiplier))
                        .multiply(BigDecimal.valueOf(branchVariance))
                        .setScale(2, RoundingMode.HALF_UP);

                    Integer quantity =
                        20 + random.nextInt(181);

                    String sku =
                        generateSku(branch, variant);

                    records.add(
                        new BranchProductVariant(
                            branch,
                            variant,
                            sku,
                            finalPrice,
                            quantity
                        )
                    );
                }
            }

            branchProductVariantRepository.saveAll(records);

            System.out.println(
                "BranchProductVariantSeeder: Seeded "
                + records.size()
                + " records."
            );
        };
    }

    private String generateSku(
        Branch branch,
        ProductVariant variant
    ) {
        return String.format(
            "%s-%d-%d",
            branch.getStore().getName()
                .replaceAll("[^A-ZA-Za-z]", "")
                .substring(0, 3)
                .toUpperCase(),
            branch.getId(),
            variant.getId()
        );
    }

    private BigDecimal determineBasePrice(
        ProductVariant variant
    ) {

        String product =
            variant.getProduct().getName();

        String size =
            variant.getName();

        // Beverages
        if (product.contains("Coca-Cola")
            || product.contains("Pepsi")
            || product.contains("Sprite")
            || product.contains("Royal")
            || product.contains("Mountain Dew")) {

            return switch (size) {
                case "250mL Can" -> BigDecimal.valueOf(22);
                case "500mL Bottle" -> BigDecimal.valueOf(35);
                case "1.5L Bottle" -> BigDecimal.valueOf(72);
                default -> BigDecimal.valueOf(30);
            };
        }

        if (product.contains("Gatorade")) {
            return switch (size) {
                case "350mL" -> BigDecimal.valueOf(28);
                case "500mL" -> BigDecimal.valueOf(42);
                default -> BigDecimal.valueOf(35);
            };
        }

        // Noodles
        if (product.contains("Lucky Me")
            || product.contains("Payless")) {

            if (size.contains("130"))
                return BigDecimal.valueOf(28);

            if (size.contains("120"))
                return BigDecimal.valueOf(22);

            return BigDecimal.valueOf(15);
        }

        // Snacks
        if (product.contains("Piattos")
            || product.contains("Nova")
            || product.contains("V-Cut")
            || product.contains("Oishi")
            || product.contains("Rinbee")
            || product.contains("Pillows")) {

            if (size.contains("130"))
                return BigDecimal.valueOf(65);

            if (size.contains("90"))
                return BigDecimal.valueOf(45);

            if (size.contains("85"))
                return BigDecimal.valueOf(42);

            return BigDecimal.valueOf(18);
        }

        // Crackers
        if (product.contains("SkyFlakes")
            || product.contains("Fita")) {

            return switch (size) {
                case "25g" -> BigDecimal.valueOf(8);
                case "30g" -> BigDecimal.valueOf(10);
                case "50g" -> BigDecimal.valueOf(15);
                case "100g" -> BigDecimal.valueOf(28);
                default -> BigDecimal.valueOf(15);
            };
        }

        // Soap
        if (product.contains("Safeguard")
            || product.contains("Dove")) {

            if (size.contains("130")
                || size.contains("135"))
                return BigDecimal.valueOf(65);

            return BigDecimal.valueOf(40);
        }

        // Toothpaste
        if (product.contains("Colgate")
            || product.contains("Closeup")) {

            if (size.contains("150"))
                return BigDecimal.valueOf(95);

            if (size.contains("100"))
                return BigDecimal.valueOf(65);

            return BigDecimal.valueOf(35);
        }

        // Detergents
        if (product.contains("Tide")
            || product.contains("Surf")
            || product.contains("Ariel")) {

            if (size.equals("1kg"))
                return BigDecimal.valueOf(180);

            if (size.contains("500"))
                return BigDecimal.valueOf(95);

            return BigDecimal.valueOf(18);
        }

        // Generic fallback
        return BigDecimal.valueOf(50);
    }
}
