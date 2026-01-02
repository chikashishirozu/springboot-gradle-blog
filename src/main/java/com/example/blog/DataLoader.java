package com.example.blog;

import com.example.blog.entity.User;
import com.example.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.Arrays;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // サンプルユーザーデータの作成
        if (userRepository.count() == 0) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setEmail("admin@example.com");
            admin.setPassword(passwordEncoder.encode("password"));
            admin.setRoles(Arrays.asList("ADMIN", "USER"));
            admin.setEnabled(true);
            admin.setAccountNonLocked(true);
            
            userRepository.save(admin);
            
            User user = new User();
            user.setUsername("user");
            user.setEmail("user@example.com");
            user.setPassword(passwordEncoder.encode("password"));
            user.setRoles(Arrays.asList("USER"));
            user.setEnabled(true);
            user.setAccountNonLocked(true);
            
            userRepository.save(user);
            
            System.out.println("サンプルユーザーデータを作成しました");
        }
    }
}
