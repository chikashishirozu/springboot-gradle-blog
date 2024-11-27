package com.example.blog;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.when;
import org.springframework.boot.test.context.SpringBootTest;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class BlogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostRepository postRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllPosts() throws Exception {
        UUID id = UUID.randomUUID();
        Post post = new Post();
        post.setId(id);
        post.setTitle("Test Post");
        post.setContents("This is a test");
        post.setUsername("Test User01");

        when(postRepository.findAll()).thenReturn(List.of(post));

        mockMvc.perform(get("/api/v1/posts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Test Post"))
                .andExpect(jsonPath("$[0].contents").value("This is a test"))
                .andExpect(jsonPath("$[0].username").value("Test User01"));
    }

    @Test
    public void testGetPostById() throws Exception {
        UUID id = UUID.randomUUID();
        Post post = new Post();
        post.setId(id);
        post.setTitle("Test Post");
        post.setContents("This is a test");
        post.setUsername("Test User01");

        when(postRepository.findById(id)).thenReturn(Optional.of(post));

        mockMvc.perform(get("/api/v1/posts/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Post"))
                .andExpect(jsonPath("$.contents").value("This is a test"))
                .andExpect(jsonPath("$.username").value("Test User01"));
    }
}



