package com.example.blog;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PostController.class)
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostRepository postRepository;

    @InjectMocks
    private PostController postController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreatePost_ValidInput() throws Exception {
        // Mocking the repository
        Post newPost = new Post();
        newPost.setTitle("Test Title");
        newPost.setContents("Test Content");

        // Calling the controller method
        mockMvc.perform(post("/posts")
                .param("title", "Test Title")
                .param("contents", "Test Content")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is3xxRedirection());

        // Capture the argument passed to postRepository.save()
        ArgumentCaptor<Post> postCaptor = ArgumentCaptor.forClass(Post.class);
        verify(postRepository).save(postCaptor.capture());
        Post savedPost = postCaptor.getValue();

        // Verify the saved post's properties
        assertNotNull(savedPost, "The saved post should not be null");
        assertEquals("Test Title", savedPost.getTitle());
        assertEquals("Test Content", savedPost.getContents());
        assertNotNull(savedPost.getCreatedAt(), "The created_at field should not be null");
    }

    @Test
    public void testCreatePost_InvalidTitle() throws Exception {
        // Mocking the repository
        Post newPost = new Post();
        newPost.setTitle("");
        newPost.setContents("Test Content");

        // Calling the controller method
        mockMvc.perform(post("/posts")
                .param("title", "")
                .param("contents", "Test Content")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}


