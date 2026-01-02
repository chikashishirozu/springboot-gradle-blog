package com.example.blog;

import com.example.blog.entity.Post;
import com.example.blog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@Controller
@RequestMapping("/blog")
public class BlogController {

    @Autowired
    private PostRepository postRepository;

    @GetMapping
    public String getAllPosts(Model model) {
        model.addAttribute("posts", postRepository.findAll());
        return "blog";
    }

    @GetMapping("/{id}")
    public String getPostById(@PathVariable UUID id, Model model) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        model.addAttribute("post", post);
        return "post";
    }

    @GetMapping("/new")
    public String showNewPostForm(Model model) {
        model.addAttribute("post", new Post());
        return "new_post";
    }

    @PostMapping
    public String createPost(@ModelAttribute Post post) {
        postRepository.save(post);
        return "redirect:/blog";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable UUID id, Model model) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        model.addAttribute("post", post);
        return "edit_post";
    }

    @PostMapping("/update/{id}")
    public String updatePost(@PathVariable UUID id, @ModelAttribute Post postDetails) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        post.setTitle(postDetails.getTitle());
        post.setContent(postDetails.getContent());
        postRepository.save(post);
        return "redirect:/blog";
    }

    @GetMapping("/delete/{id}")
    public String deletePost(@PathVariable UUID id) {
        postRepository.deleteById(id);
        return "redirect:/blog";
    }
}
