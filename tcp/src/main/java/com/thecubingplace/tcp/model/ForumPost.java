package com.thecubingplace.tcp.model;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "forum_posts")
public class ForumPost {
    @Id
    @Column(name = "post_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "thread_id")
    private ForumThread forumThread;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserDB user;

    @Column(nullable = false)
    private String content;

    @Column(name = "created_at")
    private Timestamp createdAt;
}
