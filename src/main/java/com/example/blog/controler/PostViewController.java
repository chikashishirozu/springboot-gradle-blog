package com.example.blog.controller;

import com.example.blog.entity.Post;
import com.example.blog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
     * 記事一覧画面（ページネーション対応）
     * GET /posts
     * GET /posts?page=0
     * GET /posts?page=1&size=5
     */
    @GetMapping
    public String listPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model
    ) {
        // ページネーション設定（新しい順にソート）
        Pageable pageable = PageRequest.of(
            page, 
            size, 
            Sort.by("createdAt").descending()
        );
        
        // ページング検索実行
        Page<Post> postPage = postRepository.findAll(pageable);
        
        // テンプレートに渡すデータ
        model.addAttribute("posts", postPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", postPage.getTotalPages());
        model.addAttribute("totalElements", postPage.getTotalElements());
        
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
        model.addAttribute("isEdit", false);
        return "posts/form";  // templates/posts/form.html
    }

    /**
     * 記事作成処理
     * POST /posts/new
     */
    @PostMapping("/new")
    public String createPost(
            @RequestParam String title,
            @RequestParam String content,
            Authentication authentication,
            RedirectAttributes redirectAttributes
    ) {
        try {
            String username = authentication != null ? authentication.getName() : "anonymous";
            Post post = new Post(title, content, username);
            postRepository.save(post);
            
            redirectAttributes.addFlashAttribute("message", "Post created successfully!");
            return "redirect:/posts";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to create post: " + e.getMessage());
            return "redirect:/posts/new";
        }
    }

    /**
     * 記事編集フォーム表示
     * GET /posts/edit/{id}
     */
    @GetMapping("/edit/{id}")
    public String editPostForm(@PathVariable UUID id, Model model) {
        return postRepository.findById(id)
                .map(post -> {
                    model.addAttribute("post", post);
                    model.addAttribute("isEdit", true);
                    return "posts/form";  // templates/posts/form.html
                })
                .orElse("redirect:/posts");
    }

    /**
     * 記事更新処理
     * POST /posts/edit/{id}
     */
    @PostMapping("/edit/{id}")
    public String updatePost(
            @PathVariable UUID id,
            @RequestParam String title,
            @RequestParam String content,
            RedirectAttributes redirectAttributes
    ) {
        return postRepository.findById(id)
                .map(existingPost -> {
                    existingPost.setTitle(title);
                    existingPost.setContent(content);
                    postRepository.save(existingPost);
                    
                    redirectAttributes.addFlashAttribute("message", "Post updated successfully!");
                    return "redirect:/posts";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("error", "Post not found!");
                    return "redirect:/posts";
                });
    }

    /**
     * 記事削除処理
     * POST /posts/delete/{id}
     */
    @PostMapping("/delete/{id}")
    public String deletePost(
            @PathVariable UUID id,
            RedirectAttributes redirectAttributes
    ) {
        try {
            postRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("message", "Post deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to delete post: " + e.getMessage());
        }
        return "redirect:/posts";
    }
}
