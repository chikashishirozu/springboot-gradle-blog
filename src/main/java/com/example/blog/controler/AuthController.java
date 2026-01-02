package com.example.blog.controller;

import com.example.blog.repository.UserRepository;
import com.example.blog.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import jakarta.validation.Valid;  // Use jakarta.validation if on Spring Boot 3+
import java.util.List;

@Controller
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
    
    @PostMapping("/login")
    public String logout() {
        return "redirect:/index";
    }          

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());  // Bind empty User to form
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute User user, BindingResult bindingResult, 
                           @RequestParam("confirmPassword") String confirmPassword, Model model) {
        if (bindingResult.hasErrors()) {
            return "register";  // Redisplay form with errors
        }

        if (!user.getPassword().equals(confirmPassword)) {
            model.addAttribute("passwordMismatch", "Passwords do not match");
            return "register";
        }

        // Check for existing user (optional but recommended)
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            model.addAttribute("usernameExists", "Username already taken");
            return "register";
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(List.of("ROLE_USER"));
        user.setEnabled(true);  // Ensure login works
        user.setAccountNonLocked(true);

        try {
            userRepository.save(user);
        } catch (Exception e) {
            model.addAttribute("error", "Registration failed: " + e.getMessage());
            return "register";
        }

        return "redirect:/login?registered";
    }
/*    
    @GetMapping("/index")
    public String index(Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            model.addAttribute("username", authentication.getName());
            // 例: PostRepositoryから投稿を取得
            model.addAttribute("posts", postRepository.findAll());
        }
        return "index";  // templates/index.html を返す
    }
    */
}
