package com.example.blog;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@DataJpaTest
@ActiveProfiles("test")
public class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Test
    public void testSaveAndFindPost() {
        // Creating and saving a new post
        UUID id = UUID.randomUUID();       
        Post post = new Post();
        post.setTitle("Test Post");
        post.setContents("This is a test");
        Post savedPost = postRepository.save(post);

        // Finding the post by ID and verifying the result
        Post foundPost = postRepository.findById(savedPost.getId()).orElse(null);
        assertNotNull(foundPost);
        assertEquals("Test Post", foundPost.getTitle());
        assertEquals("This is a test", foundPost.getContents());
    }
}


