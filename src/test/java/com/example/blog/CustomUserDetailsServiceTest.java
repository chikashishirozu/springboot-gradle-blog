package com.example.blog;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    private CustomUserDetailsService userDetailsService;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        userDetailsService = new CustomUserDetailsService(userRepository);
    }

    @Test
    public void testLoadUserByUsername_UserFound() {
        List<String> roles = new ArrayList<>();
        roles.add("ROLE_USER");
        User user = new User("user", "user@example.com", passwordEncoder.encode("password"), roles);
        when(userRepository.findByUsername("user")).thenReturn(Optional.of(user));

        UserDetails userDetails = userDetailsService.loadUserByUsername("user");
        assertEquals("user", userDetails.getUsername());
        assertEquals(user.getPassword(), userDetails.getPassword());
    }

    @Test
    public void testLoadUserByUsername_UserNotFound() {
        when(userRepository.findByUsername("nonexistent")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsService.loadUserByUsername("nonexistent");
        });
    }
}





