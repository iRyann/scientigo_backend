package com.thecubingplace.tcp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.thecubingplace.tcp.model.Lesson;
import com.thecubingplace.tcp.service.LessonService;
import com.thecubingplace.tcp.dto.LessonDTO;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/api/v1/lessons")
public class LessonController {
    
    private final LessonService lessonService;

    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @GetMapping
    public ResponseEntity<List<LessonDTO>> getLessons() {
        return ResponseEntity.ok(
            StreamSupport.stream(lessonService.getLessons().spliterator(), false)
                .map(LessonDTO::fromLesson)
                .collect(Collectors.toList())
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<LessonDTO> getLesson(@PathVariable Long id) {
        Lesson lesson = lessonService.getLesson(id);
        if (lesson == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(LessonDTO.fromLesson(lesson));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LessonDTO> createLesson(@RequestBody Lesson lesson) {
        return ResponseEntity.status(HttpStatus.CREATED)
                           .body(LessonDTO.fromLesson(lessonService.createLesson(lesson)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LessonDTO> updateLesson(
            @PathVariable Long id,
            @RequestBody Lesson lesson) {
        return ResponseEntity.ok(LessonDTO.fromLesson(lessonService.putLesson(id, lesson)));
    }

    @GetMapping("/search")
    public ResponseEntity<List<LessonDTO>> searchLessons(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Long courseId) {
        Iterable<Lesson> lessons;
        if (title != null) {
            lessons = lessonService.getLessonsByTitle(title);
        } else if (courseId != null) {
            lessons = lessonService.getLessonsByCourseId(courseId);
        } else {
            lessons = lessonService.getLessons();
        }
        return ResponseEntity.ok(
            StreamSupport.stream(lessons.spliterator(), false)
                .map(LessonDTO::fromLesson)
                .collect(Collectors.toList())
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteLesson(@PathVariable Long id) {
        lessonService.deleteLesson(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/course/{courseId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteLessonsByCourse(@PathVariable Long courseId) {
        lessonService.deleteLessonByCourseId(courseId);
        return ResponseEntity.noContent().build();
    }
}
