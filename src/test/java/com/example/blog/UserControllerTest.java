package com.example.blog;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
public class UserControllerTest {

    @Autowired
    private UserController userController;

    @MockBean
    private UserRepository userRepository;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetUserById() {
        List<String> roles = new ArrayList<>();
        roles.add("ROLE_USER");
        User blogUser = new User("username", "user@example.com", passwordEncoder.encode("password"), roles);
        UUID userId = UUID.randomUUID();

        when(userRepository.findById(userId)).thenReturn(Optional.of(blogUser));

        ResponseEntity<User> response = userController.getUserById(userId.toString());

        assertEquals(200, response.getStatusCode().value());
        assertEquals(blogUser, response.getBody());
    }
}


