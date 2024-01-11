package com.kood.homework.translationapi.controller;


import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import com.kood.homework.translationapi.model.ProjectLogger;
import com.kood.homework.translationapi.model.TranslationParameter;
import com.kood.homework.translationapi.service.DeeplTranslatorService;
import com.kood.homework.translationapi.service.TranslationHistoryService;
import com.kood.homework.translationapi.util.IpRateLimiter;


@TestPropertySource(locations = "classpath:application-test.properties")
@WebMvcTest(TranslationController.class)
class TranslationControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IpRateLimiter ipRateLimiter;

    @MockBean
    private ProjectLogger projectLogger;

    @MockBean
    private DeeplTranslatorService deepLTranslatorService;

    @MockBean
    private TranslationHistoryService translationHistoryService;

    @Autowired
    private TranslationController translationController;

    @Value("${translation.api.translate.ratelimit}")
    private int translateTextRateLimit;

    private static final String API_TRANSLATE_PATH = "/api/translate";
    private static final String IP_ADDRESS = "127.0.0.1";

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(translationController).build();
    }

    @Test
    void testTranslate_Success() throws Exception {
        String input = "Hello";
        String sourceLang = "EN";
        String targetLang = "FR";
        String translatedText = "Bonjour";
       

        when(ipRateLimiter.tryAcquire(API_TRANSLATE_PATH, IP_ADDRESS, translateTextRateLimit)).thenReturn(true);

        when(deepLTranslatorService.translateText(new TranslationParameter(input, sourceLang, targetLang)))
                .thenReturn(translatedText);

        doNothing().when(translationHistoryService).saveTranslation(input, translatedText, sourceLang, targetLang);

        mockMvc.perform(post(API_TRANSLATE_PATH)
                .param("input_text", input)
                .param("source_lang", sourceLang)
                .param("target_lang", targetLang)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.body.code").value(200))
                .andExpect(jsonPath("$.body.type").value("OK"))
                .andExpect(jsonPath("$.body.details").value("Successfully translated text"));
    }


    @Test
    void testTranslate_InvalidInput() throws Exception {
        String input = "Hello";
        String sourceLang = "EN";



        when(ipRateLimiter.tryAcquire(API_TRANSLATE_PATH, IP_ADDRESS, translateTextRateLimit)).thenReturn(true);


        mockMvc.perform(post(API_TRANSLATE_PATH)
                .param("input_text", input)
                .param("source_lang", sourceLang)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error.code").value(400))
                .andExpect(jsonPath("$.error.type").value("Bad Request"))
                .andExpect(jsonPath("$.error.details").value("Invalid input or target_language"));
    }


    @Test
    void testTranslate_RateLimitExceeded() throws Exception {
        String input = "Hello";
        String sourceLang = "EN";
        String targetLang = "FR";


        when(ipRateLimiter.tryAcquire(API_TRANSLATE_PATH, IP_ADDRESS, translateTextRateLimit)).thenReturn(false);

        mockMvc.perform(post(API_TRANSLATE_PATH)
                .param("input_text", input)
                .param("source_lang", sourceLang)
                .param("target_lang", targetLang)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isTooManyRequests())
                .andExpect(jsonPath("$.error.code").value(429))
                .andExpect(jsonPath("$.error.type").value("Too Many Requests"))
                .andExpect(jsonPath("$.error.details")
                        .value("Rate limit exceeded. Current rate limit: " + translateTextRateLimit));
    }
}