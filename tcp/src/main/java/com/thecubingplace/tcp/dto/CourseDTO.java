package com.thecubingplace.tcp.dto;

import com.thecubingplace.tcp.model.Course;

import lombok.Data;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class CourseDTO {
    private Long id;
    private String title;
    private String description;
    private String subject;
    private Timestamp createdAt;
    private List<LessonSummaryDTO> lessons;  // Version simplifiée des leçons

    public static CourseDTO fromCourse(Course course) {
        CourseDTO dto = new CourseDTO();
        dto.setId(course.getId());
        dto.setTitle(course.getTitle());
        dto.setDescription(course.getDescription());
        dto.setSubject(course.getSubject());
        dto.setCreatedAt(course.getCreatedAt());
        dto.setLessons(course.getLessons().stream()
            .map(LessonSummaryDTO::fromLesson)
            .collect(Collectors.toList()));
        return dto;
    }
} 