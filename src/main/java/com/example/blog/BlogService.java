package com.example.blog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.blog.Post;
import com.example.blog.BlogRepository;
import java.util.List;

@Service
public class BlogService {

    private final BlogRepository blogRepository;

    @Autowired
    public BlogService(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    public List<Post> getAllPosts() {
        return blogRepository.findAll();
    }
    
    // その他のビジネスロジックメソッドをここに追加します
}

