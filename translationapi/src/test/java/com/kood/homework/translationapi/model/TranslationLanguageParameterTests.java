package com.kood.homework.translationapi.model;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;


@TestPropertySource(locations = "classpath:application-test.properties")
@SpringBootTest
public class TranslationLanguageParameterTests {

    @Test
    void validTypeSource() {
        TranslationLanguageParameter parameter = new TranslationLanguageParameter("source");
        assertEquals("source", parameter.getType());
    }

    @Test
    void validTypeTarget() {
        TranslationLanguageParameter parameter = new TranslationLanguageParameter("target");
        assertEquals("target", parameter.getType());
    }

    @Test
    void invalidTypeNull() {
        assertThrows(IllegalArgumentException.class, () -> new TranslationLanguageParameter(null));
    }

    @Test
    void invalidTypeEmpty() {
        assertThrows(IllegalArgumentException.class, () -> new TranslationLanguageParameter(""));
    }

    @Test
    void invalidTypeUnknownValue() {
        assertThrows(IllegalArgumentException.class, () -> new TranslationLanguageParameter("random"));
    }
}