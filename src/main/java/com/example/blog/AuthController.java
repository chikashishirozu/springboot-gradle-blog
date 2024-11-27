package com.example.blog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.http.ResponseEntity; // 追加
import java.util.UUID;
import java.util.Collections;

@Controller
public class AuthController {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @PreAuthorize("hasRole('ADMIN')") // 管理者のみアクセス可能
    @GetMapping("/data")
    public ResponseEntity<String> getAdminData() {
        return ResponseEntity.ok("This is admin data.");   
    } // 閉じカッコを追加

    @PreAuthorize("hasRole('ADMIN')")  // 管理者のみアクセス可能
    @GetMapping("/admin/dashboard")
    public String adminDashboard() {
        return "admin/dashboard";  // 管理者専用のダッシュボードページ
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String username,
                               @RequestParam String email, // 追加
                               @RequestParam String password, 
                               @RequestParam String confirmPassword,
                               Model model, 
                               RedirectAttributes redirectAttributes) {
        if (userRepository.findByUsername(username) != null) {
            model.addAttribute("error", "Username already exists");
            return "register";
        }
        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match");
            return "register";
        }
        if (username.length() < 4) {
            model.addAttribute("error", "Username must be at least 4 characters long");
            return "register";
        }
        if (password.length() < 8) {
            model.addAttribute("error", "Password must be at least 8 characters long");
            return "register";
        }

        User user = new User();
        user.setId(UUID.randomUUID());
        user.setUsername(username);
        user.setEmail(email);        
        user.setPassword(passwordEncoder.encode(password));
        user.setRoles(Collections.singletonList("USER"));
        userRepository.save(user);

        redirectAttributes.addFlashAttribute("message", "Registration successful. Please log in.");
        return "redirect:/login";
    }
}

