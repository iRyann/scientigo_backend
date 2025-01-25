package com.thecubingplace.tcp.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class JWTServiceTest {
    
    @MockBean
    private JwtEncoder jwtEncoder;
    
    @Autowired
    private JWTService jwtService;
    
    private Authentication authentication;
    
    @BeforeEach
    void setUp() {
        authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("testUser");
    }
    
    @Test
    void generateToken_ShouldReturnToken() {
        Jwt jwt = mock(Jwt.class);
        when(jwt.getTokenValue()).thenReturn("testToken");
        when(jwtEncoder.encode(any())).thenReturn(jwt);
        
        String token = jwtService.generateToken(authentication);
        
        assertNotNull(token);
        assertEquals("testToken", token);
    }
} 