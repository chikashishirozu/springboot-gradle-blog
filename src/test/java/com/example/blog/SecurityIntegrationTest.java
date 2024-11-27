package com.example.blog;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.ActiveProfiles;
import com.example.blog.BlogApplication;
import com.example.blog.BlogController;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class SecurityIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "user", password = "password", roles = {"USER"})
    public void testAuthenticatedAccess() throws Exception {
        mockMvc.perform(get("/api/v1/post/new"))
            .andExpect(status().isOk());
    }

    @Test
    public void testUnauthenticatedAccess() throws Exception {
        mockMvc.perform(get("/api/v1/post/new"))
            .andExpect(status().is3xxRedirection())  // HTTP 302 Redirect when unauthenticated
            .andExpect(redirectedUrlPattern("**/login"));  // Assuming redirection to login page
    }
}



