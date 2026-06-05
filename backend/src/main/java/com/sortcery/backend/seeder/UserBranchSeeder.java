package com.sortcery.backend.seeder;

import com.sortcery.backend.model.Branch;
import com.sortcery.backend.model.Store;
import com.sortcery.backend.model.User;
import com.sortcery.backend.model.UserBranch;
import com.sortcery.backend.repository.BranchRepository;
import com.sortcery.backend.repository.StoreRepository;
import com.sortcery.backend.repository.UserBranchRepository;
import com.sortcery.backend.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.transaction.annotation.Transactional;

@Configuration
public class UserBranchSeeder {
    @Bean
    @Transactional
    @Order(4)
    CommandLineRunner seedUserBranches(
        UserBranchRepository userBranchRepository,
        BranchRepository branchRepository,
        UserRepository userRepository,
        StoreRepository storeRepository
    ) {
        return args -> {
            if (userBranchRepository.count() > 0) {
                System.out.println("UserBranchSeeder: UserBranches already exist. Skipping seed.");
                return;
            }

            List<UserBranch> userBranches = new ArrayList<>();

            Store fm = storeRepository.findByName("FreshMart").orElseThrow();
            List<Branch> fmBranches = branchRepository.findByStoreId(fm.getId());
            List<User> fmUsers = userRepository.findByEmailContaining("freshmart");
            assignUsers(userBranches, fmBranches, fmUsers);

            Store vh = storeRepository.findByName("ValueHub Stores").orElseThrow();
            List<Branch> vhBranches = branchRepository.findByStoreId(vh.getId());
            List<User> vhUsers = userRepository.findByEmailContaining("valuehub");
            assignUsers(userBranches, vhBranches, vhUsers);

            Store pc = storeRepository.findByName("PrimeChoice Market").orElseThrow();
            List<Branch> pcBranches = branchRepository.findByStoreId(pc.getId());
            List<User> pcUsers = userRepository.findByEmailContaining("primechoice");
            assignUsers(userBranches, pcBranches, pcUsers);
            
            Store cg = storeRepository.findByName("CommunityGrocer").orElseThrow();
            List<Branch> cgBranches = branchRepository.findByStoreId(cg.getId());
            List<User> cgUsers = userRepository.findByEmailContaining("communitygrocer");
            assignUsers(userBranches, cgBranches, cgUsers);

            Store ub = storeRepository.findByName("UrbanBasket").orElseThrow();
            List<Branch> ubBranches = branchRepository.findByStoreId(ub.getId());
            List<User> ubUsers = userRepository.findByEmailContaining("urbanbasket");
            assignUsers(userBranches, ubBranches, ubUsers);

            userBranchRepository.saveAll(userBranches);
            System.out.println("UserBranchSeeder: Seeded " + userBranches.size() + " userBranches.");
        };
    }

    private void assignUsers(
        List<UserBranch> userBranches, 
        List<Branch> branches,
        List<User> users
    ) {
        User manager = users.stream()
            .filter((user) -> user.getRole() == User.Role.MANAGER)
            .findFirst()
            .orElseThrow();
        List<User> retailers = users.stream()
            .filter((user) -> user.getRole() == User.Role.RETAILER)
            .toList();

        for (int i = 0; i < 2; i++) {
            userBranches.add(new UserBranch(manager, branches.get(i)));
            userBranches.add(new UserBranch(retailers.get(i), branches.get(i)));
        }
    }
}
