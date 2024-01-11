package com.kood.homework.translationapi.model;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class TranslationParameterTests {

    @Test
    void validParameters() {
        TranslationParameter parameter = new TranslationParameter("Hello", "en", "es");
        assertEquals("Hello", parameter.getInputText());
        assertEquals("en", parameter.getSourceLanguage());
        assertEquals("es", parameter.getTargetLanguage());
    }

    @Test
    void invalidInputNull() {
        assertThrows(IllegalArgumentException.class, () -> new TranslationParameter(null, "en", "es"));
    }

    @Test
    void invalidInputEmpty() {
        assertThrows(IllegalArgumentException.class, () -> new TranslationParameter("", "en", "es"));
    }

    @Test
    void invalidInputTooLong() {
        String longInput = "a".repeat(1501);
        assertThrows(IllegalArgumentException.class, () -> new TranslationParameter(longInput, "en", "es"));
    }

    @Test
    void invalidSourceLanguageNull() {
        assertThrows(IllegalArgumentException.class, () -> new TranslationParameter("Hello", null, "es"));
    }

    @Test
    void invalidSourceLanguageEmpty() {
        assertThrows(IllegalArgumentException.class, () -> new TranslationParameter("Hello", "", "es"));
    }

    @Test
    void invalidTargetLanguageNull() {
        assertThrows(IllegalArgumentException.class, () -> new TranslationParameter("Hello", "en", null));
    }

    @Test
    void invalidTargetLanguageEmpty() {
        assertThrows(IllegalArgumentException.class, () -> new TranslationParameter("Hello", "en", ""));
    }
}