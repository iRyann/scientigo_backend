package com.thecubingplace.tcp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.thecubingplace.tcp.service.ForumThreadService;
import com.thecubingplace.tcp.model.ForumThread;
import com.thecubingplace.tcp.dto.ForumThreadDTO;
import com.thecubingplace.tcp.dto.CreateThreadPostDTO;
import com.thecubingplace.tcp.configuration.CustomUserDetails;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/api/v1/forum/threads")
public class ForumThreadController {
    
    private final ForumThreadService threadService;

    public ForumThreadController(ForumThreadService threadService) {
        this.threadService = threadService;
    }

    @GetMapping
    public ResponseEntity<List<ForumThreadDTO>> getAllThreads() {
        return ResponseEntity.ok(
            StreamSupport.stream(threadService.getAllThreads().spliterator(), false)
                .map(ForumThreadDTO::fromForumThread)
                .collect(Collectors.toList())
        );
    }

    @GetMapping("/{threadId}")
    public ResponseEntity<ForumThreadDTO> getThreadById(@PathVariable Long threadId) {
        return ResponseEntity.ok(ForumThreadDTO.fromForumThread(threadService.getThreadById(threadId)));
    }


    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ForumThreadDTO>> getThreadsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(
            StreamSupport.stream(threadService.getThreadsByUserId(userId).spliterator(), false)
                .map(ForumThreadDTO::fromForumThread)
                .collect(Collectors.toList())
        );
    }

    @GetMapping("/search")
    public ResponseEntity<List<ForumThreadDTO>> searchThreads(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) Timestamp date,
            @RequestParam(required = false) String subject) {
        
        Iterable<ForumThread> threads = threadService.searchThreads(title, userId, date, subject);

        return ResponseEntity.ok(
            StreamSupport.stream(threads.spliterator(), false)
                .map(ForumThreadDTO::fromForumThread)
                .collect(Collectors.toList())
        );
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ForumThreadDTO> createThread(
        @RequestBody CreateThreadPostDTO createDTO, 
        @AuthenticationPrincipal CustomUserDetails currentUser) {
        return ResponseEntity.status(HttpStatus.CREATED)
                           .body(ForumThreadDTO.fromForumThread(threadService.createThread(createDTO, currentUser.getId())));
    }

    @DeleteMapping("/{threadId}")
    @PreAuthorize("authentication.principal.id == #thread.userId or hasRole('ADMIN')")
    public ResponseEntity<Void> deleteThread(@PathVariable Long threadId) {
        threadService.deleteThreadById(threadId);
        return ResponseEntity.noContent().build();
    }
}
