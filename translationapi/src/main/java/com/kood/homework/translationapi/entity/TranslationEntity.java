package com.kood.homework.translationapi.entity;

import jakarta.persistence.*;
import java.time.ZoneOffset;
import java.time.Instant;
import java.util.Date;


/**
 * Entity representing translations stored in the database.
 * 
 * <p>
 * <strong>Author:</strong> JesusKris
 * </p> 
 */
@Entity
@Table(name = "Translation")
public class TranslationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String input;

    @Column(nullable = false)
    private String translation;

    @Column(nullable = false)
    private String sourceLang;

    @Column(nullable = false)
    private String targetLang;

    @Column(nullable = false, updatable = false)
    @Temporal(jakarta.persistence.TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(nullable = false)
    @Temporal(jakarta.persistence.TemporalType.TIMESTAMP)
    private Date updatedAt;


    // Default constructor for JPA
    public TranslationEntity() {}


    public TranslationEntity(String input, String translation, String sourceLang, String targetLang) {
        this.input = input;
        this.translation = translation;
        this.sourceLang = sourceLang;
        this.targetLang = targetLang;
    }

    @PrePersist
    protected void onCreate() {
        createdAt = Date.from(Instant.now().atOffset(ZoneOffset.UTC).toInstant());
        updatedAt = Date.from(Instant.now().atOffset(ZoneOffset.UTC).toInstant());
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = Date.from(Instant.now().atOffset(ZoneOffset.UTC).toInstant());
    }

    public Long getId() {
        return id;
    }

    public String getInput() {
        return input;
    }

    public String getTranslation() {
        return translation;
    }

    public String getSourceLang() {
        return sourceLang;
    }

    public String getTargetLang() {
        return targetLang;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }
}