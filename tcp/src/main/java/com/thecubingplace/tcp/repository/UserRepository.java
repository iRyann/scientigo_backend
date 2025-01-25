package com.thecubingplace.tcp.repository;

import com.thecubingplace.tcp.model.UserDB;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserDB, Long> {
    Optional<UserDB> findByUsername(String username);
    Optional<UserDB> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);

    @Modifying
    @Transactional
    @Query("UPDATE UserDB u SET u.passwordHash = :updatedPasswordHash WHERE u.id = :userId")
    void updatePasswordHash(Long userId, String updatedPasswordHash);
}
