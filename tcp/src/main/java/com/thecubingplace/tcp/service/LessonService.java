package com.thecubingplace.tcp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thecubingplace.tcp.model.Lesson;
import com.thecubingplace.tcp.repository.LessonRepository;
import com.thecubingplace.tcp.exception.LessonNotFoundException;

@Service
public class LessonService {

    @Autowired
    private LessonRepository lessonRepository;

    public Iterable<Lesson> getLessonsByCourseId(Long courseId) {
        return lessonRepository.findByCourseId(courseId);
    }

    public Lesson getLesson(Long lessonId) {
        return lessonRepository.findById(lessonId)
            .orElseThrow(() -> new LessonNotFoundException("Lesson not found with id: " + lessonId));
    }

    public Iterable<Lesson> getLessons(){
        return lessonRepository.findAll();
    }

    public Lesson createLesson(Lesson lesson) {
        return lessonRepository.save(lesson);
    }

    public Lesson putLesson(Long lessonId, Lesson lesson) {
        return lessonRepository.findById(lessonId).map(existingLesson -> {
            existingLesson.setTitle(lesson.getTitle());
            existingLesson.setContent(lesson.getContent());
            return lessonRepository.save(existingLesson);
        }).orElseThrow(() -> new LessonNotFoundException("Lesson not found"));
    }

    public void deleteLesson(Long lessonId) {
        lessonRepository.deleteById(lessonId);
    }

    public void deleteLessonByCourseId(Long courseId) {
        lessonRepository.deleteByCourseId(courseId);
    }

    public Iterable<Lesson> getLessonsByTitle(String keywords) {
        if (keywords == null || keywords.trim().isEmpty()) {
            throw new IllegalArgumentException("Keywords cannot be empty");
        }
        return lessonRepository.findByTitleContainingIgnoreCase(keywords);
    }

    public Iterable<Lesson> getLessonsByCourse(String keywords) {
        return lessonRepository.findByCourseTitleContainingIgnoreCase(keywords);
    }
}
