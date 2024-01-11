package com.kood.homework.translationapi.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import com.deepl.api.Language;
import com.deepl.api.TextResult;
import com.deepl.api.Translator;
import com.kood.homework.translationapi.model.ProjectLogger;
import com.kood.homework.translationapi.model.TranslationLanguageParameter;
import com.kood.homework.translationapi.model.TranslationParameter;


@TestPropertySource(locations = "classpath:application-test.properties")
@SpringBootTest
class DeeplTranslatorServiceTests {

    private DeeplTranslatorService deeplTranslatorService;
    private Translator mockTranslator;
    private ProjectLogger mockLogger;


    @BeforeEach
    void setUp() {
        mockTranslator = mock(Translator.class);
        mockLogger = mock(ProjectLogger.class);
        deeplTranslatorService = new DeeplTranslatorService(mockTranslator, mockLogger);
    }

    
    @Test
    void testTranslateText() throws Exception {

        TranslationParameter translationRequest = new TranslationParameter("Hello", "EN", "FR");
        TextResult mockTextResult = new TextResult("Bonjour", "EN");

        when(mockTranslator.translateText(translationRequest.getInputText(), translationRequest.getSourceLanguage(), translationRequest.getTargetLanguage())).thenReturn(mockTextResult);

        String translatedText = deeplTranslatorService.translateText(translationRequest);

        assertEquals("Bonjour", translatedText);
    }


    @Test
    void testGetAvailableLanguages() throws Exception {

        TranslationLanguageParameter languageRequest = new TranslationLanguageParameter("source");
        List<Language> mockLanguages = Arrays.asList(new Language("en", "English", null), new Language("fr", "French", null));
 
        when(mockTranslator.getSourceLanguages()).thenReturn(mockLanguages);

        List<Language> availableLanguages = deeplTranslatorService.getAvailableLanguages(languageRequest);

        assertEquals(mockLanguages, availableLanguages);
    }
}
