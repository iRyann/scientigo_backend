package com.thecubingplace.tcp.model;

import java.sql.Timestamp;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import lombok.Data;
@Data
@Entity
@Table(name = "forum_threads")
public class ForumThread {

    @Id
    @Column(name = "thread_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserDB user;

    @Column(nullable = false)
    private String title;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @OneToMany(mappedBy = "forumThread", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ForumPost> posts;

    @Column(name = "subject")
    private String subject;

    @Column(nullable = false)
    private String content;
}
