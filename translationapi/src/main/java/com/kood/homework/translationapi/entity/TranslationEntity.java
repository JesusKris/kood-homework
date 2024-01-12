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

    @Column(nullable = false, name = "input_text", columnDefinition = "TEXT")
    private String inputText;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String translation;

    @Column(nullable = false, name = "source_lang")
    private String sourceLang;

    @Column(nullable = false, name = "target_lang")
    private String targetLang;

    @Column(nullable = false, updatable = false, name = "created_at")
    @Temporal(jakarta.persistence.TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(nullable = false, name = "updated_at")
    @Temporal(jakarta.persistence.TemporalType.TIMESTAMP)
    private Date updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = Date.from(Instant.now().atOffset(ZoneOffset.UTC).toInstant());
        updatedAt = Date.from(Instant.now().atOffset(ZoneOffset.UTC).toInstant());
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = Date.from(Instant.now().atOffset(ZoneOffset.UTC).toInstant());
    }

    public void setInputText(String inputText) throws IllegalArgumentException {
        if (!isValidInput(inputText)) {
            throw new IllegalArgumentException("Invalid inputText");
        }
        this.inputText = inputText;
    }

    public void setTranslation(String translation) throws IllegalArgumentException {
        if (!isValidInput(translation)) {
            throw new IllegalArgumentException("Invalid translation");
        }
        this.translation = translation;
    }

    public void setSourceLang(String sourceLang) throws IllegalArgumentException {
        if (!isValidInput(sourceLang)) {
            throw new IllegalArgumentException("Invalid sourceLang");
        }
        this.sourceLang = sourceLang;
    }

    public void setTargetLang(String targetLang) throws IllegalArgumentException {
        if (!isValidInput(targetLang)) {
            throw new IllegalArgumentException("Invalid targetLang");
        }
        this.targetLang = targetLang;
    }

    public Long getId() {
        return id;
    }

    public String getInputText() {
        return inputText;
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

    private boolean isValidInput(String input) throws IllegalArgumentException {
        return input != null && !input.isEmpty();
    }
}