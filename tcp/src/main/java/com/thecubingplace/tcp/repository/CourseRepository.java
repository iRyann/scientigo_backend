package com.thecubingplace.tcp.repository;

import org.springframework.data.repository.CrudRepository;

import com.thecubingplace.tcp.model.Course;

public interface CourseRepository extends CrudRepository<Course, Long>{

    Iterable<Course> findByTitleContainingIgnoreCase(String keyword);
    Iterable<Course> findBySubject(String keyword);
    
}
