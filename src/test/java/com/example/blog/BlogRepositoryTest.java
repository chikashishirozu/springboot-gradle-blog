package com.example.blog;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.junit.jupiter.api.Assertions.*;
import java.util.UUID;

@DataJpaTest
public class BlogRepositoryTest {

    @Autowired
    private BlogRepository blogRepository;

    @Test
    public void testSaveAndFindPost() {
        // Creating and saving a new post
        Post post = new Post();
        post.setTitle("Test Post");
        post.setContents("This is a test");
        blogRepository.save(post);

        // Finding the post by ID and verifying the result
        Post foundPost = blogRepository.findById(post.getId()).orElse(null);
        assertNotNull(foundPost);
        assertEquals("Test Post", foundPost.getTitle());
        assertEquals("This is a test", foundPost.getContents());
    }
}

