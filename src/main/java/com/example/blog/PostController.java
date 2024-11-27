package com.example.blog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;

@Controller
public class PostController {

    private final PostRepository postRepository;

    @Autowired
    public PostController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    // List all posts
    @GetMapping("/posts")
    public String index(Model model) {
        model.addAttribute("posts", postRepository.findAll());
        model.addAttribute("error", "");
        return "index";
    }

    // Create a new post
    @PostMapping("/posts")
    public String createPost(@RequestParam(value = "title", required = true) String title,
                             @RequestParam(value = "contents", required = true) String contents,
                             Model model) {
        final String TITLE_ERROR = "Title is required.";
        final String CONTENT_ERROR = "Content is required.";

        if (title == null || title.trim().isEmpty()) {
            model.addAttribute("error", TITLE_ERROR);
            model.addAttribute("posts", postRepository.findAll());
            return "index";
        }
        if (contents == null || contents.trim().isEmpty()) {
            model.addAttribute("error", CONTENT_ERROR);
            model.addAttribute("posts", postRepository.findAll());
            return "index";
        }

        savePost(title, contents);

        return "redirect:/posts";
    }

    // Edit post form
    @GetMapping("/post/edit/{id}")
    public String editPost(@PathVariable("id") UUID id, Model model, RedirectAttributes redirectAttributes) {
        Post post = postRepository.findById(id)
                                  .orElse(null);

        if (post == null) {
            redirectAttributes.addFlashAttribute("error", "Post not found.");
            return "redirect:/posts";
        }

        model.addAttribute("post", post);
        return "edit_post";
    }

    // Update post
    @PostMapping("/post/edit/{id}")
    public String updatePost(@PathVariable("id") UUID id,
                             @RequestParam(value = "title", required = true) String title,
                             @RequestParam(value = "contents", required = true) String contents,
                             RedirectAttributes redirectAttributes) {
        Post post = postRepository.findById(id)
                                  .orElse(null);

        if (post == null) {
            redirectAttributes.addFlashAttribute("error", "Post not found.");
            return "redirect:/posts";
        }

        // Validate title and contents
        if (title == null || title.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Title is required.");
            return "redirect:/post/edit/" + id;
        }
        if (contents == null || contents.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Content is required.");
            return "redirect:/post/edit/" + id;
        }

        // Update the post
        post.setTitle(title);
        post.setContents(contents);
        postRepository.save(post);

        return "redirect:/posts";
    }

    // Delete post
    @PostMapping("/post/delete/{id}")
    public String deletePost(@PathVariable("id") UUID id, RedirectAttributes redirectAttributes) {
        Post post = postRepository.findById(id)
                                  .orElse(null);

        if (post == null) {
            redirectAttributes.addFlashAttribute("error", "Post not found.");
            return "redirect:/posts";
        }

        postRepository.delete(post);
        redirectAttributes.addFlashAttribute("message", "Post deleted successfully.");
        return "redirect:/admin/dashboard";
    }

    // Save post method (used by create and update)
    private void savePost(String title, String contents) {
        Post post = new Post();
        post.setTitle(title);
        post.setContents(contents);
        postRepository.save(post);
    }
}




