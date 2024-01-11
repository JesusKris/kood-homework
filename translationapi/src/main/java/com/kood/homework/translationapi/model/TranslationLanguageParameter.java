package com.kood.homework.translationapi.model;


import java.util.Set;


/**
 * Represents parameters for language-related requests.
 * It includes the type of language group (source or target).
 * 
 * <p>
 * Used for verification of the request query parameters.
 * 
 * <p>
 * <strong>Author:</strong> JesusKris
 * </p>
 */
public class TranslationLanguageParameter {
    private final String type;

    private static final Set<String> VALID_TYPES = Set.of("source", "target");


    /**
     * Constructs a new TranslationLanguageParameter with the specified type.
     *
     * @param type The type of language group (source or target).
     * @throws IllegalArgumentException If the language group type is invalid.
     */
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
        return VALID_TYPES.contains(type);
    }
}