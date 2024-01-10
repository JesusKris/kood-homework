package com.kood.homework.translationapi.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.kood.homework.translationapi.entity.TranslationEntity;
import java.util.List;


/**
 * Spring Data JPA repository interface for managing {@link TranslationEntity}
 * entities.
 * 
 * <p>
 * <strong>Author:</strong> JesusKris
 * </p>
 */
public interface TranslationRepository extends JpaRepository<TranslationEntity, Long> {

    
    /**
     * Save and flush a translation entity.
     *
     * @param translation The translation entity to be saved and flushed.
     * @return The saved and flushed translation entity.
     */
    TranslationEntity saveAndFlush(TranslationEntity translation);


    /**
     * Retrieve all translations ordered by creation timestamp in descending order.
     *
     * @return A list of translation entities ordered by creation timestamp in
     *         descending order.
     */
    List<TranslationEntity> findAllByOrderByCreatedAtDesc();


    /**
     * Retrieve all translations ordered by creation timestamp in ascending order.
     *
     * @return A list of translation entities ordered by creation timestamp in
     *         ascending order.
     */
    List<TranslationEntity> findAllByOrderByCreatedAtAsc();
}