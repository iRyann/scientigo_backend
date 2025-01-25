package com.thecubingplace.tcp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thecubingplace.tcp.repository.CourseRepository;
import com.thecubingplace.tcp.model.Course;
import com.thecubingplace.tcp.exception.CourseNotFoundException;

@Service
public class CourseService {

    @Autowired
    CourseRepository courseRepository;

    public Iterable<Course> getCourses() {
        return courseRepository.findAll();
    }

    public Course getCourse(Long id) {
        return courseRepository.findById(id).orElseThrow(() -> new CourseNotFoundException(id));
    }

    public Course saveCourse(Course course) {
        return courseRepository.save(course);
    }

    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }

    public Course putCourse(Long courseId, Course courseUpdated) {
        return courseRepository.findById(courseId).map(existingCourse -> {
            existingCourse.setTitle(courseUpdated.getTitle());
            existingCourse.setDescription(courseUpdated.getDescription());
            existingCourse.setSubject(courseUpdated.getSubject());
            return courseRepository.save(existingCourse);
        }).orElseThrow(() -> new CourseNotFoundException("Course not found for ID " + courseId));
    }

    public Iterable<Course> getCoursesByTitle(String keyword) {
        return courseRepository.findByTitleContainingIgnoreCase(keyword);
    }

    public Iterable<Course> getCoursesBySubject(String keyword) {
        return courseRepository.findBySubject(keyword);
    }

}
