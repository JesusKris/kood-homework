package com.kood.homework.translationapi.service;


import java.util.List;
import java.lang.System.Logger.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.deepl.api.*;
import com.kood.homework.translationapi.model.ProjectLogger;
import com.kood.homework.translationapi.model.TranslationLanguageParameter;
import com.kood.homework.translationapi.model.TranslationParameter;


/**
 * Service class responsible for handling translation operations using the DeepL API.
 * This includes translating text and retrieving available languages for translation.
 * 
 * <p>
 * <strong>Author:</strong> JesusKris
 * </p>
 */
@Service
public class DeeplTranslatorService {

    private final Translator translator;

    @Autowired
    private ProjectLogger logger;

    
    public DeeplTranslatorService(Translator translator,  ProjectLogger logger) {
        this.translator = translator;
        this.logger = logger;
    }


    /**
     * Translates the input text from the source language to the target language using the DeepL API.
     *
     * @param translationRequest The parameter for the translation request.
     * @return The translated text.
     * @throws Exception If an error occurs during the translation process.
     */
    public String translateText(TranslationParameter translationRequest) throws Exception {
        logger.log(Level.INFO, "Attempting to translate a text..");

        TextResult translatedTextRes = translator.translateText(translationRequest.getInputText(),
                 translationRequest.getSourceLanguage(),
                translationRequest.getTargetLanguage());

        logger.log(Level.INFO, "Successfully translated a text..");

        return translatedTextRes.getText();
    }


    /**
     * Retrieves the available languages for translation based on the request type (source or target).
     *
     * @param languageRequest The parameters for the language request.
     * @return A list of Language objects representing the available languages.
     * @throws Exception If an error occurs while fetching available languages.
     */
    public List<Language> getAvailableLanguages(TranslationLanguageParameter translationLanguageParameter) throws Exception {
        logger.log(Level.INFO, "Attempting to get available translation languages..");

        List<Language> languageRes = "source".equals(translationLanguageParameter.getType())
                ? translator.getSourceLanguages()
                : translator.getTargetLanguages();

        logger.log(Level.INFO, "Successfully got available translation languages..");

        return languageRes;
    }
}
