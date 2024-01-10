package com.kood.homework.translationapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import com.kood.homework.translationapi.entity.TranslationEntity;
import com.kood.homework.translationapi.model.TranslationHistoryRequest;
import com.kood.homework.translationapi.repository.TranslationRepository;

@Service
public class TranslationHistoryService {

    @Autowired
    private TranslationRepository translationRepository;


    public void saveTranslation(String input, String translation, String sourceLang, String targetLang) {
        TranslationEntity translationEntity = new TranslationEntity(input, translation, sourceLang, targetLang);
        translationRepository.save(translationEntity);
    }

    public List<TranslationEntity> getTranslationHistory(TranslationHistoryRequest translationHistoryRequest) {
        return "asc".equals(translationHistoryRequest.getOrder()) ? translationRepository.findAllByOrderByCreatedAtAsc() : translationRepository.findAllByOrderByCreatedAtDesc();
    }
}