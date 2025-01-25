package com.thecubingplace.tcp.service;

import java.util.Optional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

import com.thecubingplace.tcp.exception.UserNotFoundException;
import com.thecubingplace.tcp.model.UserDB;
import com.thecubingplace.tcp.repository.UserRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public Optional<UserDB> getUser(final Long id) {
        return userRepository.findById(id);
    }

    public Optional<UserDB> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Iterable<UserDB> getUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(final Long id) {
        userRepository.deleteById(id);
    }

    public UserDB saveUser(UserDB user) {
        user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
        return userRepository.save(user);
    }

    public UserDB updateUser(Long userId, UserDB userUpdated) {
        return userRepository.findById(userId).map(existingUser -> {
            existingUser.setUsername(userUpdated.getUsername());
            existingUser.setEmail(userUpdated.getEmail());
            if (userUpdated.getPasswordHash() != null) {
                existingUser.setPasswordHash(
                    passwordEncoder.encode(userUpdated.getPasswordHash())
                );
            }
            return userRepository.save(existingUser);
        }).orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    public void updatePassword(Long userId, String updatedPasswordHash){
        userRepository.updatePasswordHash(userId, updatedPasswordHash);
    }
}
