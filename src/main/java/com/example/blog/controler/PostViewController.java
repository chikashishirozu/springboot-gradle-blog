package com.example.blog.controller;

import com.example.blog.entity.Post;
import com.example.blog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.UUID;

/**
 * HTML画面表示専用コントローラー
 * Thymeleafテンプレートを返す
 */
@Controller
@RequestMapping("/posts")
public class PostViewController {

    @Autowired
    private PostRepository postRepository;

    /**
     * 記事一覧画面
     * GET /posts
     */
    @GetMapping
    public String listPosts(Model model) {
        List<Post> posts = postRepository.findAll();
        model.addAttribute("posts", posts);
        return "posts/list";  // templates/posts/list.html
    }

    /**
     * 記事詳細画面
     * GET /posts/{id}
     */
    @GetMapping("/{id}")
    public String viewPost(@PathVariable UUID id, Model model) {
        return postRepository.findById(id)
                .map(post -> {
                    model.addAttribute("post", post);
                    return "posts/detail";  // templates/posts/detail.html
                })
                .orElse("redirect:/posts");  // 存在しなければ一覧に戻る
    }

    /**
     * 記事作成フォーム表示
     * GET /posts/new
     */
    @GetMapping("/new")
    public String newPostForm(Model model) {
        model.addAttribute("post", new Post());
        return "posts/form";  // templates/posts/form.html
    }
}
