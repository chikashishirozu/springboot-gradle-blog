package com.example.blog;

import com.example.blog.entity.User;
import com.example.blog.entity.Post;
import com.example.blog.repository.UserRepository;
import com.example.blog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PostRepository postRepository;

    @GetMapping("/dashboard")
    public String adminDashboard(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "admin/dashboard";
    }

    @GetMapping("/users/new")
    public String showCreateUserForm(Model model) {
        model.addAttribute("user", new User());
        return "admin/create_user";
    }

    @PostMapping("/users")
    public String saveUser(@ModelAttribute("user") User user) {
        userRepository.save(user);
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/posts/new")
    public String showCreatePostForm(Model model) {
        model.addAttribute("post", new Post());
        return "admin/create_post";
    }

    @PostMapping("/posts")
    public String savePost(@ModelAttribute("post") Post post) {
        postRepository.save(post);
        return "redirect:/admin/dashboard";
    }
}
