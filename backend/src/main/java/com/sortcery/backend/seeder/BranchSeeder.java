package com.sortcery.backend.seeder;

import com.sortcery.backend.model.Branch;
import com.sortcery.backend.model.Store;
import com.sortcery.backend.repository.BranchRepository;
import com.sortcery.backend.repository.StoreRepository;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.transaction.annotation.Transactional;

@Configuration
public class BranchSeeder {
    @Bean
    @Transactional
    @Order(3)
    CommandLineRunner seedBranches(BranchRepository branchRepository, StoreRepository storeRepository) {
        return args -> {
            if (branchRepository.count() > 0) {
                System.out.println("BranchSeeder: Branches already exist. Skipping seed.");
                return;
            }

            Store fm = storeRepository.findByName("FreshMart").orElseThrow();
            Store vh = storeRepository.findByName("ValueHub Stores").orElseThrow();
            Store pc = storeRepository.findByName("PrimeChoice Market").orElseThrow();
            Store cg = storeRepository.findByName("CommunityGrocer").orElseThrow();
            Store ub = storeRepository.findByName("UrbanBasket").orElseThrow();

            List<Branch> branches = List.of(
                new Branch(fm, "Quezon City", 14.6760, 121.0437),
                new Branch(fm, "Makati", 14.5547, 121.0244),

                new Branch(vh, "Manila", 14.5995, 120.9842),
                new Branch(vh, "Pasig", 14.5764, 121.0851),

                new Branch(pc, "Taguig", 14.5176, 121.0509),
                new Branch(pc, "Quezon City", 14.6760, 121.0437),

                new Branch(cg, "Caloocan", 14.6507, 120.9746),
                new Branch(cg, "Marikina", 14.6507, 121.1029),

                new Branch(ub, "Cubao", 14.6191, 121.0509),
                new Branch(ub, "Ortigas", 14.5869, 121.0567)
            );

            branchRepository.saveAll(branches);
            System.out.println("BranchSeeder: Seeded " + branches.size() + " branches.");
        };
    }
}
