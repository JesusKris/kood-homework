package com.kood.homework.translationapi.model;


public class TranslationLanguageParameter {
    private final String type;

    public TranslationLanguageParameter(String type)
            throws IllegalArgumentException {
            

        if (!isValidType(type)) {
            throw new IllegalArgumentException("Invalid language group type");
        }

        this.type = type;
    }

    public String getType() {
        return type;
    }

    private static boolean isValidType(String type) {
        return "source".equals(type) || "target".equals(type);
    }
}