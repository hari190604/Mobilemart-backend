package com.mobilemart.backend.config;

import com.mobilemart.backend.entity.Role;
import com.mobilemart.backend.entity.User;
import com.mobilemart.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        seedAdminUser();
    }

    private void seedAdminUser() {
        if (userRepository.findFirstByEmail("admin@mobilemart.com").isEmpty()) {
            User adminUser = new User();
            adminUser.setEmail("admin@mobilemart.com");
            adminUser.setPassword(passwordEncoder.encode("admin@123"));
            adminUser.setRole(Role.ADMIN);
            adminUser.setEnabled(true);
            adminUser.setFullName("System Administrator");
            adminUser.setUsername("admin");
            adminUser.setMobileNumber("0000000000"); // Dummy 10-digit mobile number
            
            userRepository.save(adminUser);
            System.out.println("Default Admin User seeded correctly: admin@mobilemart.com / admin@123");
        }

        // Force enable all users to handle schema update default false issues
        java.util.List<User> users = userRepository.findAll();
        for (User u : users) {
             if (!u.isEnabled()) {
                 u.setEnabled(true);
                 userRepository.save(u);
             }
        }
    }
}
