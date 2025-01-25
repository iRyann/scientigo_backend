package com.thecubingplace.tcp.dto;

import com.thecubingplace.tcp.model.ForumPost;
import lombok.Data;
import java.sql.Timestamp;

@Data
public class ForumPostDTO {
    private Long id;
    private Long threadId;
    private Long userId;
    private String username;
    private String content;
    private Timestamp createdAt;

    public static ForumPostDTO fromForumPost(ForumPost post) {
        ForumPostDTO dto = new ForumPostDTO();
        dto.setId(post.getId());
        dto.setThreadId(post.getForumThread().getId());
        dto.setUserId(post.getUser().getId());
        dto.setUsername(post.getUser().getUsername());
        dto.setContent(post.getContent());
        dto.setCreatedAt(post.getCreatedAt());
        return dto;
    }

    public ForumPost toForumPost() {
        ForumPost post = new ForumPost();
        post.setId(id);
        post.setForumThread(null);
        post.setUser(null);
        post.setContent(content);
        post.setCreatedAt(createdAt);
        return post;
    }
} 