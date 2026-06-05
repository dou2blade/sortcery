package com.sortcery.backend.seeder;

import com.sortcery.backend.model.Brand;
import com.sortcery.backend.repository.BrandRepository;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.transaction.annotation.Transactional;

@Configuration
public class BrandSeeder {
    @Bean
    @Transactional
    @Order(5)
    CommandLineRunner seedBrands(BrandRepository brandRepository) {
        return args -> {
            if (brandRepository.count() > 0) {
                System.out.println("BrandSeeder: Brands already exist. Skipping seed.");
                return;
            }

            List<String> brandNames = List.of(
                // Coffee & Milk
                "Nescafé", "Milo", "Bear Brand", "Coffee Mate", "Alaska", "Anchor", "Magnolia",

                // Soft Drinks & Beverages
                "Coca-Cola", "Sprite", "Royal", "Pepsi", "Mountain Dew", "Gatorade",

                // Noodles & Pantry
                "Lucky Me", "Payless", "Del Monte", "UFC",

                // Snacks
                "Oishi", "Piattos", "Nova", "V-Cut", "Rinbee", "Pillows", "SkyFlakes", "Fita",

                // Personal Care
                "Safeguard", "Dove", "Palmolive", "Cream Silk", "Colgate", "Closeup",

                // Laundry & Cleaning
                "Tide", "Surf", "Ariel", "Joy", "Zonrox"
            );

            List<Brand> brands = brandNames.stream()
                .map(Brand::new)
                .toList();

            brandRepository.saveAll(brands);
            System.out.println("BrandSeeder: Seeded " + brands.size() + " brands.");
        };
    }
}
