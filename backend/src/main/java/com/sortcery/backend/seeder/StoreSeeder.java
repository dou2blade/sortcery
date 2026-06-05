package com.sortcery.backend.seeder;

import com.sortcery.backend.model.Store;
import com.sortcery.backend.repository.StoreRepository;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.transaction.annotation.Transactional;

@Configuration
public class StoreSeeder {
    @Bean
    @Transactional
    @Order(2)
    CommandLineRunner seedStores(StoreRepository storeRepository) {
        return args -> {
            if (storeRepository.count() > 0) {
                System.out.println("StoreSeeder: Stores already exist. Skipping seed.");
                return;
            }

            List<String> storeNames = List.of(
                "FreshMart",
                "ValueHub Stores",
                "PrimeChoice Market",
                "CommunityGrocer",
                "UrbanBasket"
            );
            List<Store> stores = storeNames.stream()
                .map(Store::new)
                .toList();

            storeRepository.saveAll(stores);
            System.out.println("StoreSeeder: Seeded " + stores.size() + " stores.");
        };
    }
}
