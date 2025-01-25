package com.thecubingplace.tcp.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.thecubingplace.tcp.exception.UserNotFoundException;
import com.thecubingplace.tcp.model.UserDB;
import com.thecubingplace.tcp.repository.UserRepository;

@SpringBootTest
class UserServiceTest {
    
    @Autowired
    private UserService userService;
    
    @MockBean
    private UserRepository userRepository;
    
    private UserDB testUser;
    
    @BeforeEach
    void setUp() {
        testUser = new UserDB();
        testUser.setId(1L);
        testUser.setUsername("testUser");
        testUser.setEmail("test@test.com");
        testUser.setPasswordHash("password123");
    }
    
    @Test
    void getUserShouldReturnUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        
        Optional<UserDB> found = userService.getUser(1L);
        
        assertAll(
            () -> assertTrue(found.isPresent()),
            () -> assertEquals(testUser.getUsername(), found.get().getUsername())
        );
    }
    
    @Test
    void getUsersShouldReturnAllUsers() {
        UserDB user2 = new UserDB();
        user2.setId(2L);
        when(userRepository.findAll()).thenReturn(Arrays.asList(testUser, user2));
        
        Iterable<UserDB> users = userService.getUsers();
        
        assertEquals(2, ((List<UserDB>) users).size());
    }
    
    @Test
    void saveUserShouldEncodePasswordAndSaveUser() {
        when(userRepository.save(any(UserDB.class))).thenReturn(testUser);
        
        UserDB savedUser = userService.saveUser(testUser);
        
        verify(userRepository).save(any(UserDB.class));
        assertTrue(new BCryptPasswordEncoder().matches("password123", savedUser.getPasswordHash()));
    }
    
    @Test
    void putUserShouldUpdateExistingUser() {
        UserDB updatedUser = new UserDB();
        updatedUser.setUsername("updatedName");
        updatedUser.setEmail("updated@test.com");
        updatedUser.setPasswordHash("newPassword");
        
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(userRepository.save(any(UserDB.class))).thenReturn(updatedUser);
        
        UserDB result = userService.updateUser(1L, updatedUser);
        
        assertAll(
            () -> assertEquals("updatedName", result.getUsername()),
            () -> assertEquals("updated@test.com", result.getEmail()),
            () -> verify(userRepository).save(any(UserDB.class))
        );
    }
    
    @Test
    void putUserShouldThrowExceptionWhenUserNotFound() {
        when(userRepository.findById(999L)).thenReturn(Optional.empty());
        
        assertThrows(UserNotFoundException.class, () -> {
            userService.updateUser(999L, testUser);
        });
    }
    
    @Test
    void deleteUserShouldCallRepository() {
        userService.deleteUser(1L);
        
        verify(userRepository).deleteById(1L);
    }
}
