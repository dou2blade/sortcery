package com.sortcery.backend.seeder;

import com.sortcery.backend.model.Branch;
import com.sortcery.backend.model.Store;
import com.sortcery.backend.repository.BranchRepository;
import com.sortcery.backend.repository.StoreRepository;
import com.sortcery.backend.service.MapService;

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
    CommandLineRunner seedBranches(
            BranchRepository branchRepository,
            StoreRepository storeRepository,
            MapService mapService
        ) {
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
            new Branch(fm, "Quezon City",
                "Visayas Avenue, San Miguel Village, Pasong Tamo, 6th District, Quezon City, Eastern Manila District, Metro Manila, 1100, Philippines",
                14.6760, 121.0437),

            new Branch(fm, "Makati",
                "Makati Avenue, Urdaneta, District I, Makati, Southern Manila District, Metro Manila, 1226, Philippines",
                14.5547, 121.0244),

            new Branch(vh, "Manila",
                "JG Plaza, 718, P. Paterno Street, Barangay 383, Barangay 307, Quiapo, Third District, Manila, Capital District, Metro Manila, 1001, Philippines",
                14.5995, 120.9842),

            new Branch(vh, "Pasig",
                "C. Raymundo Avenue, Maybunga, Pasig Second District, Pasig, Eastern Manila District, Metro Manila, 1607, Philippines",
                14.5764, 121.0851),

            new Branch(pc, "Taguig",
                "Marichu R. Tiñga Avenue, Pinagsama, Taguig District 2, Taguig, Southern Manila District, Metro Manila, 1630, Philippines",
                14.5176, 121.0509),

            new Branch(pc, "Quezon City",
                "Visayas Avenue, San Miguel Village, Pasong Tamo, 6th District, Quezon City, Eastern Manila District, Metro Manila, 1100, Philippines",
                14.6760, 121.0437),

            new Branch(cg, "Caloocan",
                "Caloocan Central Elementary School, P. Zamora Street, Barangay 15, Zone 2, Poblacion, District 2, Caloocan, Northern Manila District, Metro Manila, 1408, Philippines",
                14.6507, 120.9746),

            new Branch(cg, "Marikina",
                "Bayan-Bayanan Avenue, Amang Rodriguez Village, Concepcion Uno, District II, Marikina, Eastern Manila District, Metro Manila, 1807, Philippines",
                14.6507, 121.1029),

            new Branch(ub, "Cubao",
                "Vista Hotel, EDSA, San Martin de Porres, Cubao, 4th District, Quezon City, Eastern Manila District, Metro Manila, 1111, Philippines",
                14.6191, 121.0509),

            new Branch(ub, "Ortigas",
                "EDSA, Wack-Wack Greenhills, Mandaluyong, Eastern Manila District, Metro Manila, 1555, Philippines",
                14.5869, 121.0567)
            );

            branchRepository.saveAll(branches);
            System.out.println("BranchSeeder: Seeded " + branches.size() + " branches.");
        };
    }
}
