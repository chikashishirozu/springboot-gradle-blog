package com.example.blog;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BlogServiceTest {

    @InjectMocks
    private BlogService blogService;

    @Mock
    private BlogRepository blogRepository;

    @Test
    public void testGetAllPosts() {
        // Mocking the repository layer
        when(blogRepository.findAll()).thenReturn(List.of(new Post("Test Post", "This is a test")));

        // Calling the service method and verifying the result
        List<Post> posts = blogService.getAllPosts();
        assertEquals(1, posts.size());
        assertEquals("Test Post", posts.get(0).getTitle());
    }
}

