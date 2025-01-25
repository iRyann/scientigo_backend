package com.thecubingplace.tcp.dto;

import com.thecubingplace.tcp.model.Lesson;

import lombok.Data;
import java.sql.Timestamp;

@Data
public class LessonDTO {
    private Long id;
    private Long courseId;
    private String courseTitle;  // Juste le titre du cours
    private String title;
    private String content;
    private Timestamp createdAt;

    public static LessonDTO fromLesson(Lesson lesson) {
        LessonDTO dto = new LessonDTO();
        dto.setId(lesson.getId());
        dto.setCourseId(lesson.getCourse().getId());
        dto.setCourseTitle(lesson.getCourse().getTitle());
        dto.setTitle(lesson.getTitle());
        dto.setContent(lesson.getContent());
        dto.setCreatedAt(lesson.getCreatedAt());
        return dto;
    }
} 