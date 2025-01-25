package com.thecubingplace.tcp.service;

import org.springframework.stereotype.Service;

import java.sql.Timestamp;

import com.thecubingplace.tcp.repository.ForumPostRepository;
import com.thecubingplace.tcp.dto.CreateForumPostDTO;
import com.thecubingplace.tcp.model.ForumPost;
import com.thecubingplace.tcp.repository.ForumThreadRepository;
import com.thecubingplace.tcp.exception.ForumThreadNotFoundException;
import com.thecubingplace.tcp.exception.UserNotFoundException;
import com.thecubingplace.tcp.repository.UserRepository;

@Service
public class ForumPostService {
    
    private ForumPostRepository postRepository;
    private ForumThreadRepository threadRepository;
    private UserRepository userRepository;

    ForumPostService(ForumPostRepository postRepository, ForumThreadRepository threadRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.threadRepository = threadRepository;
        this.userRepository = userRepository;
    }

    public ForumPost createPost(CreateForumPostDTO createDTO, Long userId) {
        ForumPost post = createDTO.toForumPost();
        post.setForumThread(threadRepository.findById(createDTO.getThreadId()).orElseThrow(() -> new ForumThreadNotFoundException("Thread not found")));
        post.setUser(userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found")));
        return postRepository.save(post);
    }

    public Iterable<ForumPost> getAllPosts() {
        return postRepository.findAll();
    }

    public Iterable<ForumPost> getPostsByUserId(Long userId) {
        return postRepository.findByUserId(userId);
    }

    public Iterable<ForumPost> getPostsByThreadId(Long threadId) {
        return postRepository.findByForumThreadId(threadId);
    }

    public Iterable<ForumPost> getPostsByCreatedAt(Timestamp createdAt) {
        return postRepository.findByCreatedAt(createdAt);
    }

    public Iterable<ForumPost> getPostsByUserIdAndThreadId(Long userId, Long threadId) {
        return postRepository.findByUserIdAndForumThreadId(userId, threadId);
    }

    public Iterable<ForumPost> getPostsByContentContaining(String content) {
        return postRepository.findByContentContaining(content);
    }

    public ForumPost savePost(ForumPost post) {
        return postRepository.save(post);
    }

    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }

    public void deleteAllPosts() {
        postRepository.deleteAll();
    }

    public long countPosts() {
        return postRepository.count();
    }

    public boolean existsById(Long postId) {
        return postRepository.existsById(postId);
    }

    public Iterable<ForumPost> findAllPosts() {
        return postRepository.findAll();
    }

}
