package com.thecubingplace.tcp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.thecubingplace.tcp.model.UserDB;
import com.thecubingplace.tcp.service.UserService;
import com.thecubingplace.tcp.exception.UserNotFoundException;
import com.thecubingplace.tcp.dto.UserDTO;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    @PreAuthorize("authentication.principal.id == #id or hasRole('ADMIN')")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        return userService.getUser(id)
            .map(user -> ResponseEntity.ok(UserDTO.fromUser(user)))
            .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDTO>> getUsers() {
        return ResponseEntity.ok(
            StreamSupport.stream(userService.getUsers().spliterator(), false)
                .map(UserDTO::fromUser)
                .collect(Collectors.toList())
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("authentication.principal.id == #id or hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("authentication.principal.id == #id or hasRole('ADMIN')")
    public ResponseEntity<UserDTO> updateUser(
            @PathVariable Long id, 
            @RequestBody UserDB userUpdated) {
        return ResponseEntity.ok(
            UserDTO.fromUser(userService.updateUser(id, userUpdated))
        );
    }

    @PutMapping("/{id}/password")
    @PreAuthorize("authentication.principal.id == #id or hasRole('ADMIN')")
    public ResponseEntity<Void> updatePassword(
            @PathVariable Long id,
            @RequestBody PasswordUpdateRequest request) {
        userService.updatePassword(id, request.updatedPassword());
        return ResponseEntity.noContent().build();
    }
}

record PasswordUpdateRequest(String updatedPassword) {}

