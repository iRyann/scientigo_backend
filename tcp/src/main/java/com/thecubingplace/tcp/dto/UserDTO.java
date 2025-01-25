package com.thecubingplace.tcp.dto;

import com.thecubingplace.tcp.model.UserDB;
import lombok.Data;
import java.sql.Timestamp;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private UserDB.UserRole role;
    private Timestamp createdAt;
    // Pas de passwordHash !

    public static UserDTO fromUser(UserDB user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        dto.setCreatedAt(user.getCreatedAt());
        return dto;
    }
} 