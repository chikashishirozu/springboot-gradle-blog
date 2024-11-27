package com.example.blog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.Collections;
import java.util.Arrays;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        User user1 = new User();
        user1.setUsername("john_doe");
        user1.setPassword(passwordEncoder.encode("password123"));
        user1.setRoles(Arrays.asList("ROLE_USER"));
        userRepository.save(user1);

        User user2 = new User();
        user2.setUsername("jane_smith");
        user2.setPassword(passwordEncoder.encode("password456"));
        user2.setRoles(Arrays.asList("ROLE_ADMIN"));
        userRepository.save(user2);    
     }
}

