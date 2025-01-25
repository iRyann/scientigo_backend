package com.thecubingplace.tcp.configuration;

import com.thecubingplace.tcp.exception.UserNotFoundException;
import com.thecubingplace.tcp.exception.CourseNotFoundException;
import com.thecubingplace.tcp.exception.LessonNotFoundException;
import com.thecubingplace.tcp.exception.FavoriteNotFoundException;
import com.thecubingplace.tcp.exception.ForumPostNotFoundException;
import com.thecubingplace.tcp.exception.ForumThreadNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler({
        UserNotFoundException.class, 
        CourseNotFoundException.class, 
        LessonNotFoundException.class,
        FavoriteNotFoundException.class,
        ForumThreadNotFoundException.class,
        ForumPostNotFoundException.class,
    })
    public ResponseEntity<String> handleNotFoundException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        logger.error("Une erreur interne s'est produite", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("Une erreur interne s'est produite. Veuillez réessayer plus tard.");
    }
}

