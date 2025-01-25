package com.thecubingplace.tcp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import lombok.Data;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

@Data
@Entity
@Table(name = "items")
public class Item {

    public enum ItemType {
        LESSON,
        COURSE
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false, name = "item_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "item_type")
    private ItemType type;

    // Relation avec Lesson (peut être null si c'est un Course)
    @ManyToOne
    @JoinColumn(name = "lesson_id", referencedColumnName = "lesson_id", nullable = true)
    private Lesson lesson;

    // Relation avec Course (peut être null si c'est une Lesson)
    @ManyToOne
    @JoinColumn(name = "course_id", referencedColumnName = "course_id", nullable = true)
    private Course course;

    // Validation : une seule des deux relations doit être définie
    @PrePersist
    @PreUpdate
    private void validateItem() {
        if ((lesson == null && course == null) || (lesson != null && course != null)) {
            throw new IllegalStateException("An item must be linked to either a Lesson or a Course, but not both.");
        }
    }
}
