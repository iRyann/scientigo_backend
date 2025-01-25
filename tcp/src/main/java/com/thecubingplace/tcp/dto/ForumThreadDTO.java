package com.thecubingplace.tcp.dto;

import com.thecubingplace.tcp.model.ForumThread;
import lombok.Data;
import java.sql.Timestamp;
import java.util.List;

@Data
public class ForumThreadDTO {
    private Long id;
    private String title;
    private Long userId;
    private String username;
    private Timestamp createdAt;
    private String subject;
    private List<ForumPostDTO> recentPosts;  // Optionnel : les N posts les plus récents

    public static ForumThreadDTO fromForumThread(ForumThread thread) {
        ForumThreadDTO dto = new ForumThreadDTO();
        dto.setId(thread.getId());
        dto.setTitle(thread.getTitle());
        dto.setUserId(thread.getUser().getId());
        dto.setUsername(thread.getUser().getUsername());
        dto.setCreatedAt(thread.getCreatedAt());
        dto.setSubject(thread.getSubject());
        return dto;
    }
} 