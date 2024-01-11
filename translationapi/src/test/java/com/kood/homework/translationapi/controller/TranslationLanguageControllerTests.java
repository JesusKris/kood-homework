package com.kood.homework.translationapi.controller;


import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.deepl.api.Language;
import com.kood.homework.translationapi.model.ProjectLogger;
import com.kood.homework.translationapi.model.TranslationLanguageParameter;
import com.kood.homework.translationapi.service.DeeplTranslatorService;
import com.kood.homework.translationapi.util.IpRateLimiter;


@TestPropertySource(locations = "classpath:application-test.properties")
@WebMvcTest(TranslationLanguageController.class)
class TranslationLanguageControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IpRateLimiter ipRateLimiter;

    @MockBean
    private DeeplTranslatorService deepLTranslatorService;

    @MockBean
    private ProjectLogger logger;

    @Autowired
    private TranslationLanguageController translationLanguageController;

    @Value("${translation.api.language.ratelimit}")
    private int availableLanguagesRateLimit;

    private static final String API_LANGUAGE_PATH = "/api/translate/languages";
    private static final String IP_ADDRESS = "127.0.0.1";

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(translationLanguageController).build();
    }

    @Test
    void testGetSupportedLanguages_Success() throws Exception {

        when(ipRateLimiter.tryAcquire(API_LANGUAGE_PATH, IP_ADDRESS, availableLanguagesRateLimit)).thenReturn(true);

        String urlParameter = "source";

        List<Language> supportedLanguages = Arrays.asList(new Language("en", "English", false), new Language("fr", "French", false));
        when(deepLTranslatorService.getAvailableLanguages(new TranslationLanguageParameter(urlParameter))).thenReturn(supportedLanguages);


        mockMvc.perform(get(API_LANGUAGE_PATH)
                .param("type", urlParameter)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.body.code").value(200))
                .andExpect(jsonPath("$.body.type").value("OK"))
                .andExpect(jsonPath("$.body.details").value("Successfully returned available languages for type: source"));
    }

    @Test
    void testGetSupportedLanguages_InvalidInput() throws Exception {

        when(ipRateLimiter.tryAcquire(API_LANGUAGE_PATH, IP_ADDRESS, availableLanguagesRateLimit)).thenReturn(true);


        mockMvc.perform(get(API_LANGUAGE_PATH)
                .param("type", "invalidType")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error.code").value(400))
                .andExpect(jsonPath("$.error.type").value("Bad Request"))
                .andExpect(jsonPath("$.error.details").value("Invalid language group type"));
    }
}