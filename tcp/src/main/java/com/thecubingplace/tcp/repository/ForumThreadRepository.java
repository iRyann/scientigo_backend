package com.thecubingplace.tcp.repository;

import java.sql.Timestamp;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.thecubingplace.tcp.model.ForumPost;
import com.thecubingplace.tcp.model.ForumThread;

@Repository
public interface ForumThreadRepository extends CrudRepository<ForumThread, Long> {

    Iterable<ForumThread> findByUserId(Long userId);
    Iterable<ForumThread> findByTitle(String title);
    Iterable<ForumThread> findByCreatedAt(Timestamp createdAt);
    Iterable<ForumThread> findByUserIdAndTitle(Long userId, String title);
    Iterable<ForumThread> findBySubject(String subject);

    @Query("SELECT f FROM ForumThread f " +
           "WHERE (:title IS NULL OR f.title LIKE %:title%) " +
           "AND (:userId IS NULL OR f.user.id = :userId) " +
           "AND (:createdAt IS NULL OR f.createdAt = :createdAt) " +
           "AND (:subject IS NULL OR f.subject LIKE %:subject%)")
    Iterable<ForumThread> searchThreads(
        @Param("title") String title,
        @Param("userId") Long userId,
        @Param("createdAt") Timestamp date,
        @Param("subject") String subject);

}
