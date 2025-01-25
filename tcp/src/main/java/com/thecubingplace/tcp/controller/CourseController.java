package com.thecubingplace.tcp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.thecubingplace.tcp.model.Course;
import com.thecubingplace.tcp.service.CourseService;
import com.thecubingplace.tcp.dto.CourseDTO;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/api/v1/courses")
public class CourseController {
    
    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public ResponseEntity<List<CourseDTO>> getCourses() {
        return ResponseEntity.ok(
            StreamSupport.stream(courseService.getCourses().spliterator(), false)
                .map(CourseDTO::fromCourse)
                .collect(Collectors.toList())
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseDTO> getCourse(@PathVariable Long id) {
        return ResponseEntity.ok(CourseDTO.fromCourse(courseService.getCourse(id)));
    }

    @PostMapping
    @PreAuthorize("isAuthenticated() and hasRole('ADMIN')")
    public ResponseEntity<CourseDTO> saveCourse(@RequestBody Course course) {
        return ResponseEntity.status(HttpStatus.CREATED)
                           .body(CourseDTO.fromCourse(courseService.saveCourse(course)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CourseDTO> putCourse(
            @PathVariable Long id,
            @RequestBody Course course) {
        return ResponseEntity.ok(CourseDTO.fromCourse(courseService.putCourse(id, course)));
    }

    @GetMapping("/search")
    public ResponseEntity<List<CourseDTO>> searchCourses(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String subject) {
        Iterable<Course> courses;
        if (title != null) {
            courses = courseService.getCoursesByTitle(title);
        } else if (subject != null) {
            courses = courseService.getCoursesBySubject(subject);
        } else {
            courses = courseService.getCourses();
        }
        return ResponseEntity.ok(
            StreamSupport.stream(courses.spliterator(), false)
                .map(CourseDTO::fromCourse)
                .collect(Collectors.toList())
        );
    }
}
