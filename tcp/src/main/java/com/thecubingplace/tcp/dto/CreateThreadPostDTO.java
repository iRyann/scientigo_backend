package com.thecubingplace.tcp.dto;

import com.thecubingplace.tcp.model.ForumThread;
import lombok.Data;

@Data
public class CreateThreadPostDTO {
    private String title;
    private String content;

    public ForumThread toForumThread() {
        ForumThread thread = new ForumThread();
        thread.setTitle(title);
        thread.setContent(content);
        return thread;
    }
}