package com.thecubingplace.tcp.exception;

import java.sql.Timestamp;

public class ForumThreadNotFoundException extends RuntimeException {
    public ForumThreadNotFoundException(Long id) {
        super("Forum thread with id " + id + " not found.");
    }

    public ForumThreadNotFoundException(String title) {
        super("Forum thread with title " + title + " not found.");
    }

    public ForumThreadNotFoundException(Long userId, String title) {
        super("Forum thread with userId " + userId + " and title " + title + " not found.");
    }

    public ForumThreadNotFoundException(Timestamp createdAt) {
        super("Forum thread with createdAt " + createdAt + " not found.");
    }

    public ForumThreadNotFoundException() {
        super("Forum thread not found.");
    }

}
