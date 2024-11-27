package com.example.blog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    // 管理者ダッシュボード
    @GetMapping
    public String adminDashboard(Model model) {
        List<Post> posts = postRepository.findAll();
        List<User> users = userRepository.findAll();
        model.addAttribute("posts", posts);
        model.addAttribute("users", users);
        return "admin/dashboard"; // admin/dashboard.html テンプレートをレンダリング
    }

    // 記事の追加ページ
    @GetMapping("/posts/new")
    public String createPostForm(Model model) {
        model.addAttribute("post", new Post());
        return "admin/create_post"; // admin/create_post.html テンプレートをレンダリング
    }

    // 記事の追加処理
    @PostMapping("/posts")
    public String savePost(@ModelAttribute("post") Post post) {
        postRepository.save(post);
        return "redirect:/admin";
    }

    // ユーザーの追加ページ
    @GetMapping("/users/new")
    public String createUserForm(Model model) {
        model.addAttribute("user", new User());
        return "admin/create_user"; // admin/create_user.html テンプレートをレンダリング
    }

    // ユーザーの追加処理
    @PostMapping("/users")
    public String saveUser(@ModelAttribute("user") User user) {
        userRepository.save(user);
        return "redirect:/admin";
    }

    // 他のCRUDメソッド（記事とユーザーの編集・削除）も追加可能
}


