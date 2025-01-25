package com.thecubingplace.tcp.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.thecubingplace.tcp.model.Lesson;

public interface LessonRepository extends CrudRepository<Lesson, Long> {

    List<Lesson> findByCourseId(Long courseId);
    void deleteByCourseId(Long courseId);
    Iterable<Lesson> findByTitleContainingIgnoreCase(String keywords);
    Iterable<Lesson> findByCourseTitleContainingIgnoreCase(String keywords);
}
