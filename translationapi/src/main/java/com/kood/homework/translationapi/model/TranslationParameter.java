package com.kood.homework.translationapi.model;


/**
 * Represents parameters for a translation request.
 * It includes the input text, source language, and target language.
 *
 * <p>Used for verification of the request query parameters.
 * 
 * <p>
 * <strong>Author:</strong> JesusKris
 * </p>
 */
public class TranslationParameter {
    private final String inputText;
    private final String sourceLanguage;
    private final String targetLanguage;


    /**
     * Constructs a new TranslationParameter with the specified input text, source language, and target language.
     *
     * @param inputText      The text to be translated.
     * @param sourceLanguage The language of the input text.
     * @param targetLanguage The target language for translation.
     * @throws IllegalArgumentException If the input or target_language is invalid.
     */
    public TranslationParameter(String inputText, String sourceLanguage, String targetLanguage) throws IllegalArgumentException {
        if (!isValidInputText(inputText) || !isValidLanguage(targetLanguage) || !isValidLanguage(sourceLanguage)) {
            throw new IllegalArgumentException("Invalid input or target_language");
        }

        this.inputText = inputText;
        this.sourceLanguage = sourceLanguage;
        this.targetLanguage = targetLanguage;
    }

    public String getInputText() {
        return inputText;
    }

    public String getSourceLanguage() {
        return sourceLanguage;
    }

    public String getTargetLanguage() {
        return targetLanguage;
    }

    private static boolean isValidInputText(String inputText) {
        return inputText != null && !inputText.isEmpty() && inputText.length() <= 1500;
    }

    private static boolean isValidLanguage(String targetLanguage) {
        return targetLanguage != null && !targetLanguage.isEmpty();
    }
}