package com.thecubingplace.tcp.service;

import java.sql.Timestamp;

import org.springframework.stereotype.Service;

import com.thecubingplace.tcp.dto.CreateThreadPostDTO;
import com.thecubingplace.tcp.exception.ForumThreadNotFoundException;
import com.thecubingplace.tcp.model.ForumPost;
import com.thecubingplace.tcp.model.ForumThread;
import com.thecubingplace.tcp.repository.ForumThreadRepository;
import com.thecubingplace.tcp.repository.UserRepository;
import com.thecubingplace.tcp.exception.UserNotFoundException;

@Service
public class ForumThreadService {

    private ForumThreadRepository threadRepository;
    private UserRepository userRepository;

    ForumThreadService(ForumThreadRepository threadRepository, UserRepository userRepository) {
        this.threadRepository = threadRepository;
        this.userRepository = userRepository;
    }

    public Iterable<ForumThread> getAllThreads() {
        return threadRepository.findAll();
    }

    public ForumThread getThreadById(Long threadId) {
        return threadRepository.findById(threadId).orElseThrow(() -> new ForumThreadNotFoundException(threadId));
    }

    public Iterable<ForumThread> getThreadsByUserId(Long userId) {
        return threadRepository.findByUserId(userId);
    }

    public Iterable<ForumThread> getThreadsByTitle(String title) {
        return threadRepository.findByTitle(title);
    }

    public Iterable<ForumThread> getThreadsByCreatedAt(Timestamp createdAt) {
        return threadRepository.findByCreatedAt(createdAt);
    }

    public Iterable<ForumThread> getThreadsByUserIdAndTitle(Long userId, String title) {
        return threadRepository.findByUserIdAndTitle(userId, title);
    }

    public Iterable<ForumThread> getThreadsBySubject(String subject) {
        return threadRepository.findBySubject(subject);
    }

      public Iterable<ForumThread> searchThreads(String title, Long userId, Timestamp date, String subject) {
        return threadRepository.searchThreads(title, userId, date, subject);
    }

    public ForumThread createThread(CreateThreadPostDTO createDTO, Long userId) {
        ForumThread thread = createDTO.toForumThread();
        thread.setUser(userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found")));
        return threadRepository.save(thread);
    }

    public ForumThread saveThread(ForumThread thread){
        return threadRepository.save(thread);
    }

    public void deleteThread(ForumThread thread){
        threadRepository.delete(thread);
    }

    public void deleteThreadById(Long threadId){
        threadRepository.deleteById(threadId);
    }
}
