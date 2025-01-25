package com.thecubingplace.tcp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.thecubingplace.tcp.service.ForumPostService;
import com.thecubingplace.tcp.model.ForumPost;
import com.thecubingplace.tcp.dto.ForumPostDTO;
import com.thecubingplace.tcp.configuration.CustomUserDetails;
import com.thecubingplace.tcp.dto.CreateForumPostDTO;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/api/v1/forum/posts")
public class ForumPostController {
    
    private final ForumPostService postService;
    
    public ForumPostController(ForumPostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<List<ForumPostDTO>> getAllPosts() {
        return ResponseEntity.ok(
            StreamSupport.stream(postService.getAllPosts().spliterator(), false)
                .map(ForumPostDTO::fromForumPost)
                .collect(Collectors.toList())
        );
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ForumPostDTO>> getPostsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(
            StreamSupport.stream(postService.getPostsByUserId(userId).spliterator(), false)
                .map(ForumPostDTO::fromForumPost)
                .collect(Collectors.toList())
        );
    }

    @GetMapping("/thread/{threadId}")
    public ResponseEntity<List<ForumPostDTO>> getPostsByThreadId(@PathVariable Long threadId) {
        return ResponseEntity.ok(
            StreamSupport.stream(postService.getPostsByThreadId(threadId).spliterator(), false)
                .map(ForumPostDTO::fromForumPost)
                .collect(Collectors.toList())
        );
    }

    @GetMapping("/search")
    public ResponseEntity<List<ForumPostDTO>> searchPosts(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) Long threadId,
            @RequestParam(required = false) String content) {
        Iterable<ForumPost> posts;
        if (userId != null && threadId != null) {
            posts = postService.getPostsByUserIdAndThreadId(userId, threadId);
        } else if (content != null) {
            posts = postService.getPostsByContentContaining(content);
        } else {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(
            StreamSupport.stream(posts.spliterator(), false)
                .map(ForumPostDTO::fromForumPost)
                .collect(Collectors.toList())
        );
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ForumPostDTO> createPost(
        @RequestBody CreateForumPostDTO createDTO,
        @AuthenticationPrincipal CustomUserDetails currentUser
    ) {
        ForumPost newPost = postService.createPost(createDTO, currentUser.getId());
        return ResponseEntity.status(HttpStatus.CREATED)
                            .body(ForumPostDTO.fromForumPost(newPost));
    }

    @DeleteMapping("/{postId}")
    @PreAuthorize("authentication.principal.id == #post.userId or hasRole('ADMIN')")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return ResponseEntity.noContent().build();
    }
}

