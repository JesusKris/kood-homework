package com.kood.homework.translationapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.deepl.api.*;
import com.kood.homework.translationapi.model.TranslationLanguageParameter;
import com.kood.homework.translationapi.model.TranslationParameter;

@Service
public class DeepLTranslatorService {

    private final Translator translator;

    public DeepLTranslatorService(@Value("${deepl.api.key}") String apiKey) {
        this.translator = new Translator(apiKey);
    }

    public String translateText(TranslationParameter translationRequest) throws Exception {
        try {

            TextResult translatedTextRes = translator.translateText(translationRequest.getInputText(),
                    translationRequest.getSourceLanguage(),
                    translationRequest.getTargetLanguage());

            return translatedTextRes.getText();
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid input or target_language");
        }
    }

    public Object getAvailaableLanguages(TranslationLanguageParameter languageRequest) throws Exception {
        try {

            List<Language> languageRes = "source".equals(languageRequest.getType()) ? translator.getSourceLanguages()
                    : translator.getTargetLanguages();

            return languageRes;
        } catch (Exception e) {
            throw new Exception(e);
        }
    }
}