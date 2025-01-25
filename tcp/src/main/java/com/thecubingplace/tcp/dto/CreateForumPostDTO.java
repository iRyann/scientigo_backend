package com.thecubingplace.tcp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.sql.Timestamp;

import com.thecubingplace.tcp.model.ForumPost;

import lombok.Data;

@Data
public class CreateForumPostDTO {
    @NotNull
    private Long threadId;

    @NotBlank
    private String content;

    public ForumPost toForumPost() {
        ForumPost post = new ForumPost();
        post.setContent(content);
        post.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        return post;
    }

} 