package com.example.blog;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.blog.BlogApplication;
import com.example.blog.Post;
import com.example.blog.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
//import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

//import java.util.List;
import java.util.UUID;

@AutoConfigureMockMvc
@ActiveProfiles("test")
@SpringBootTest(classes = BlogApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BlogControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;   
    
    @BeforeEach
    public void setUp() {
        // Clear the repository and add mock data
        postRepository.deleteAll();
        UUID id = UUID.randomUUID();
        Post post = new Post();
        post.setId(id);
        post.setTitle("Test Post");
        post.setContents("This is a test"); // 修正箇所
        post.setUsername("test_user");      
        postRepository.save(post);
    }

    @Test
    public void testGetAllPosts() throws Exception {
        // Execute request and verify response
        mockMvc.perform(get("/api/v1/posts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Test Post"))
                .andExpect(jsonPath("$[0].contents").value("This is a test")); // 修正箇所
    }    
}

