package com.kood.homework.translationapi.model;

public class TranslationParameter {
    private final String inputText;
    private final String sourceLanguage;
    private final String targetLanguage;

    public TranslationParameter(String inputText, String sourceLanguage, String targetLanguage)
            throws IllegalArgumentException {

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