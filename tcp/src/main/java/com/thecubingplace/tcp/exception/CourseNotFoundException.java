package com.thecubingplace.tcp.exception;

public class CourseNotFoundException extends RuntimeException {
    public CourseNotFoundException(Long id) {
        super("Could not find course " + id);
    }
    public CourseNotFoundException(String message) {
        super(message);
    }
}
