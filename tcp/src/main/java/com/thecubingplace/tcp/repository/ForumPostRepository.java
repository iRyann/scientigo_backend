package com.thecubingplace.tcp.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

import com.thecubingplace.tcp.model.ForumPost;

@Repository
public interface ForumPostRepository extends CrudRepository<ForumPost, Long> {

    Iterable<ForumPost> findByUserId(Long userId);
    Iterable<ForumPost> findByForumThreadId(Long forumThreadId);
    Iterable<ForumPost> findByCreatedAt(Timestamp createdAt);
    Iterable<ForumPost> findByUserIdAndForumThreadId(Long userId, Long forumThreadId);

    @Query("SELECT p FROM ForumPost p WHERE p.content LIKE %:content%")
    Iterable<ForumPost> findByContentContaining(@Param("content") String content);
}
