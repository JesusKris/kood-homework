package com.kood.homework.translationapi.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.lang.System.Logger.Level;
import com.kood.homework.translationapi.entity.TranslationEntity;
import com.kood.homework.translationapi.model.ProjectLogger;
import com.kood.homework.translationapi.model.TranslationHistoryParameter;
import com.kood.homework.translationapi.repository.TranslationRepository;


/**
 * Service responsible for managing translation history operations.
 * 
 * This includes saving new translation entries and retrieving the translation
 * history.
 * 
 * <p>
 * <strong>Author:</strong> JesusKris
 * </p>
 */
@Service
public class TranslationHistoryService {

    @Autowired
    private TranslationRepository translationRepository;

    @Autowired
    private ProjectLogger logger;


    /**
     * Saves a translation entry to the database.
     *
     * @param input       The input text to be translated.
     * @param translation The translated text.
     * @param sourceLang  The source language of the input.
     * @param targetLang  The target language of the translation.
     */
    public void saveTranslation(String input, String translation, String sourceLang, String targetLang) throws Exception {
        logger.log(Level.INFO, "Attempting to save a new translation..");

        TranslationEntity translationEntity = new TranslationEntity();
        translationEntity.setInputText(input);
        translationEntity.setTranslation(translation);
        translationEntity.setSourceLang(sourceLang);
        translationEntity.setTargetLang(targetLang);

        translationRepository.save(translationEntity);

        logger.log(Level.INFO, "Successfully saved a new translation..");
    }


    /**
     * Retrieves the translation history from the database based on order of
     * creation date.
     *
     * @param historyParameter The parameter for querying translation history.
     * 
     * @return A list of TranslationEntity objects representing the translation
     *         history.
     */
    public List<TranslationEntity> getTranslationHistory(TranslationHistoryParameter historyParameter) throws Exception {
        logger.log(Level.INFO, "Attempting to retrive translation history..");

        List<TranslationEntity> translationHistory = "asc".equals(historyParameter.getOrder())
                ? translationRepository.findAllByOrderByCreatedAtAsc()
                : translationRepository.findAllByOrderByCreatedAtDesc();

        logger.log(Level.INFO, "Successfully retrived translation history..");

        return translationHistory;
    }
}