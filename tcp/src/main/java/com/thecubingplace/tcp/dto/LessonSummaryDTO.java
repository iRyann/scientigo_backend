package com.thecubingplace.tcp.dto;

import com.thecubingplace.tcp.model.Lesson;

import lombok.Data;
import java.sql.Timestamp;

@Data
public class LessonSummaryDTO {
    private Long id;
    private String title;
    private Timestamp createdAt;

    public static LessonSummaryDTO fromLesson(Lesson lesson) {
        LessonSummaryDTO dto = new LessonSummaryDTO();
        dto.setId(lesson.getId());
        dto.setTitle(lesson.getTitle());
        dto.setCreatedAt(lesson.getCreatedAt());
        return dto;
    }
} 