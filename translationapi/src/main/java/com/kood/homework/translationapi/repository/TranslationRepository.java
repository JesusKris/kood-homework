package com.kood.homework.translationapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.kood.homework.translationapi.entity.TranslationEntity;
import java.util.List;

public interface TranslationRepository extends JpaRepository<TranslationEntity, Long> {
    
    // Save method
    TranslationEntity saveAndFlush(TranslationEntity translation);

    // Custom method to get all translations with optional sorting
    List<TranslationEntity> findAllByOrderByCreatedAtDesc();
    List<TranslationEntity> findAllByOrderByCreatedAtAsc();
    
}