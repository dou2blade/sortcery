package com.sortcery.backend.seeder;

import com.sortcery.backend.model.User;
import com.sortcery.backend.repository.UserRepository;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@Configuration
public class UserSeeder {
    private final BCryptPasswordEncoder bCryptEncoder = new BCryptPasswordEncoder(10);

    @Bean
    @Transactional
    @Order(1)
    CommandLineRunner seedUsers(UserRepository userRepository) {
        return args -> {
            if (userRepository.count() > 0) {
                System.out.println("UserSeeder: Users already exist. Skipping seed.");
                return;
            }

            List<User> users = List.of(
                new User("Super", "", "Admin", "superadmin@sortcery.com", "password", User.Role.ADMIN),

                instantiateUser("Elara", "Moonfall", "FreshMart", User.Role.MANAGER),
                instantiateUser("Thalion", "Mooncrest", "Freshmart", User.Role.RETAILER),
                instantiateUser("Nyssa", "Starweaver", "Freshmart", User.Role.RETAILER),

                instantiateUser("Lyra", "Silverleaf", "ValueHub", User.Role.MANAGER),
                instantiateUser("Orion", "Ravenshade", "ValueHub", User.Role.RETAILER),
                instantiateUser("Lucian", "Everhart", "ValueHub", User.Role.RETAILER),

                instantiateUser("Darius", "Ironroot", "PrimeChoice", User.Role.MANAGER),
                instantiateUser("Selene", "Frostwhisper", "PrimeChoice", User.Role.RETAILER),
                instantiateUser("Freya", "Goldbloom", "PrimeChoice", User.Role.RETAILER),

                instantiateUser("Aldric", "Stormborn", "CommunityGrocer", User.Role.MANAGER),
                instantiateUser("Corwin", "Blackmere", "CommunityGrocer", User.Role.RETAILER),
                instantiateUser("Rowan", "Emberfall", "CommunityGrocer", User.Role.RETAILER),

                instantiateUser("Kael", "Thornwood", "UrbanBasket", User.Role.MANAGER),
                instantiateUser("Vaelor", "Nightwind", "UrbanBasket", User.Role.RETAILER),
                instantiateUser("Seraphine", "Ashvale", "UrbanBasket", User.Role.RETAILER)
            );

            userRepository.saveAll(users);

            System.out.println("UserSeeder: Seeded " + users.size() + " users.");
        };
    }

    private User instantiateUser(String firstName, String lastName, String storeName, User.Role role) {
        String email = firstName.toLowerCase() 
            + lastName.toLowerCase() 
            + "." 
            + role.toString().toLowerCase() 
            + "@" 
            + storeName.toLowerCase() 
            + ".com";

        return new User(firstName, "", lastName, email, bCryptEncoder.encode("password"), role);
    }
}
