package com.example.blog;

import com.example.blog.entity.Post;
import com.example.blog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BlogService {

    @Autowired
    private BlogRepository blogRepository;

    public List<Post> getAllPosts() {
        return blogRepository.findAll();
    }

    public Post getPostById(String id) {
        return blogRepository.findById(java.util.UUID.fromString(id))
                .orElseThrow(() -> new RuntimeException("Post not found"));
    }

    public Post savePost(Post post) {
        return blogRepository.save(post);
    }

    public void deletePost(String id) {
        blogRepository.deleteById(java.util.UUID.fromString(id));
    }
}
