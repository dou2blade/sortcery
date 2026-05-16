package com.sortcery.backend.seeder;

import com.sortcery.backend.model.User;
import com.sortcery.backend.dto.user.UserRequestDTO;
import com.sortcery.backend.repository.UserRepository;
import com.sortcery.backend.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.transaction.annotation.Transactional;

@Configuration
public class AdminSeeder {
    @Bean
    @Transactional
    @Order(1)
    CommandLineRunner seedAdmins(UserRepository userRepository, UserService userService) {
        return args -> {
            if (userRepository.count() == 0) {
                
                UserRequestDTO userInfo = new UserRequestDTO();
                userInfo.setEmail("superadmin@sortcery.com");
                userInfo.setFirstName("Super");
                userInfo.setLastName("Admin");
                userInfo.setPassword("qyL2jVlerK8rzu8Ey");
                userInfo.setRole(User.Role.ADMIN);

                userService.save(userInfo);
            } else {
                System.out.println("UserSeeder: Users already exist. Skipping seed.");
            }
        };
    }
}
