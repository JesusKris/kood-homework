package com.kood.homework.translationapi.model;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;


@TestPropertySource(locations = "classpath:application-test.properties")
@SpringBootTest
public class TranslationHistoryParameterTests {

    @Test
    void validOrderAsc() {
        TranslationHistoryParameter parameter = new TranslationHistoryParameter("asc");
        assertEquals("asc", parameter.getOrder());
    }

    @Test
    void validOrderDesc() {
        TranslationHistoryParameter parameter = new TranslationHistoryParameter("desc");
        assertEquals("desc", parameter.getOrder());
    }

    @Test
    void invalidOrderNull() {
        assertThrows(IllegalArgumentException.class, () -> new TranslationHistoryParameter(null));
    }

    @Test
    void invalidOrderEmptyString() {
        assertThrows(IllegalArgumentException.class, () -> new TranslationHistoryParameter(""));
    }

    @Test
    void invalidOrderUnknownValue() {
        assertThrows(IllegalArgumentException.class, () -> new TranslationHistoryParameter("random"));
    }
}