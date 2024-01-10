package com.kood.homework.translationapi.entity;

import jakarta.persistence.*;
import java.time.ZoneOffset;
import java.time.Instant;
import java.util.Date;

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

    // Getter for 'id'
    public Long getId() {
        return id;
    }

    // Getter for 'input'
    public String getInput() {
        return input;
    }

    // Getter for 'translation'
    public String getTranslation() {
        return translation;
    }

    // Getter for 'sourceLang'
    public String getSourceLang() {
        return sourceLang;
    }

    // Getter for 'targetLang'
    public String getTargetLang() {
        return targetLang;
    }

    // Getter for 'createdAt'
    public Date getCreatedAt() {
        return createdAt;
    }

    // Getter for 'updatedAt'
    public Date getUpdatedAt() {
        return updatedAt;
    }

}
