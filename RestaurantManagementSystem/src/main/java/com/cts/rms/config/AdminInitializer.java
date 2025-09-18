package com.cts.rms.config;

import com.cts.rms.model.User;
import com.cts.rms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (!userRepository.findByEmail("admin@restho.com").isPresent()) {
            User adminUser = new User();
            adminUser.setUserName("Admin");
            adminUser.setEmail("admin@restho.com");
            adminUser.setPassword(passwordEncoder.encode("admin123"));
            adminUser.setRole("ADMIN");
            userRepository.save(adminUser);
            System.out.println("Default admin user has been created!");
        }
    }
}