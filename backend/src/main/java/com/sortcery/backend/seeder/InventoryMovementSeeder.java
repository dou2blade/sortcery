package com.sortcery.backend.seeder;

import com.sortcery.backend.model.Branch;
import com.sortcery.backend.model.BranchProductVariant;
import com.sortcery.backend.model.InventoryMovement;
import com.sortcery.backend.model.User;
import com.sortcery.backend.model.UserBranch;
import com.sortcery.backend.repository.BranchProductVariantRepository;
import com.sortcery.backend.repository.InventoryMovementRepository;
import com.sortcery.backend.repository.UserBranchRepository;
import com.sortcery.backend.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.transaction.annotation.Transactional;

@Configuration
public class InventoryMovementSeeder {
    @Bean
    @Transactional
    @Order(10)
    CommandLineRunner seedInventoryMovements(
        InventoryMovementRepository inventoryMovementRepository,
        BranchProductVariantRepository branchProductVariantRepository,
        UserRepository userRepository,
        UserBranchRepository userBranchRepository
    ) {
        return args -> {
            if (inventoryMovementRepository.count() > 0) {
                System.out.println(
                    "InventoryMovementSeeder: Already seeded. Skipping."
                );
                return;
            }

            Random random = new Random();

            List<InventoryMovement> movements = new ArrayList<>();

            List<BranchProductVariant> variants =
                branchProductVariantRepository.findAll();

            for (BranchProductVariant bpv : variants) {
                Branch branch = bpv.getBranch();

                List<User> users = userBranchRepository.findByBranchId(branch.getId())
                    .stream()
                    .map(UserBranch::getUser)
                    .toList();
                User retailer = users.stream()
                    .filter((user) -> user.getRole() == User.Role.RETAILER)
                    .findFirst()
                    .get();
                User manager = users.stream()
                    .filter((user) -> user.getRole() == User.Role.MANAGER)
                    .findFirst()
                    .get();

                int currentQuantity = 0;

                // Initial stock
                int stockIn = 100 + random.nextInt(151);

                currentQuantity += stockIn;

                movements.add(
                    new InventoryMovement(
                        bpv,
                        InventoryMovement.Type.STOCK_IN,
                        stockIn,
                        currentQuantity,
                        "Initial stock received",
                        manager
                    )
                );

                // Random sales
                int saleCount = 2 + random.nextInt(4);

                for (int i = 0; i < saleCount; i++) {

                    int sold =
                        Math.min(
                            currentQuantity,
                            1 + random.nextInt(15)
                        );

                    currentQuantity -= sold;

                    movements.add(
                        new InventoryMovement(
                            bpv,
                            InventoryMovement.Type.SALE,
                            -sold,
                            currentQuantity,
                            "Retail sale",
                            retailer
                        )
                    );
                }

                // Optional return
                if (random.nextDouble() < 0.30) {

                    int returned =
                        1 + random.nextInt(3);

                    currentQuantity += returned;

                    movements.add(
                        new InventoryMovement(
                            bpv,
                            InventoryMovement.Type.RETURN,
                            returned,
                            currentQuantity,
                            "Customer return",
                            retailer
                        )
                    );
                }

                //
                if (random.nextDouble() < 0.20) {

                    int damaged =
                        Math.min(
                            currentQuantity,
                            1 + random.nextInt(2)
                        );

                    currentQuantity -= damaged;

                    movements.add(
                        new InventoryMovement(
                            bpv,
                            InventoryMovement.Type.DAMAGED,
                            -damaged,
                            currentQuantity,
                            "Damaged during handling",
                            retailer
                        )
                    );
                }

                // Optional expired
                if (random.nextDouble() < 0.10) {

                    int expired =
                        Math.min(
                            currentQuantity,
                            1 + random.nextInt(2)
                        );

                    currentQuantity -= expired;

                    movements.add(
                        new InventoryMovement(
                            bpv,
                            InventoryMovement.Type.EXPIRED,
                            -expired,
                            currentQuantity,
                            "Expired stock removed",
                            manager
                        )
                    );
                }

                // Optional adjustment
                if (random.nextDouble() < 0.25) {

                    boolean positive =
                        random.nextBoolean();

                    int adjustment =
                        1 + random.nextInt(5);

                    if (positive) {

                        currentQuantity += adjustment;

                        movements.add(
                            new InventoryMovement(
                                bpv,
                                InventoryMovement.Type.ADJUSTMENT_IN,
                                adjustment,
                                currentQuantity,
                                "Inventory recount correction",
                                manager
                            )
                        );

                    } else {

                        adjustment =
                            Math.min(
                                adjustment,
                                currentQuantity
                            );

                        currentQuantity -= adjustment;

                        movements.add(
                            new InventoryMovement(
                                bpv,
                                InventoryMovement.Type.ADJUSTMENT_OUT,
                                -adjustment,
                                currentQuantity,
                                "Inventory recount correction",
                                manager
                            )
                        );
                    }
                }

                // Final reconciliation
                int targetQuantity =
                    bpv.getQuantity();

                int delta =
                    targetQuantity - currentQuantity;

                if (delta != 0) {

                    InventoryMovement.Type type =
                        delta > 0
                            ? InventoryMovement.Type.ADJUSTMENT_IN
                            : InventoryMovement.Type.ADJUSTMENT_OUT;

                    movements.add(
                        new InventoryMovement(
                            bpv,
                            type,
                            delta,
                            targetQuantity,
                            "Seeder reconciliation",
                            manager
                        )
                    );
                }
            }

            inventoryMovementRepository.saveAll(movements);

            System.out.println(
                "InventoryMovementSeeder: Seeded "
                    + movements.size()
                    + " movements."
            );
        };
    }
}
