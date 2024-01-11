package com.kood.homework.translationapi.controller;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.ArrayList;
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
import com.kood.homework.translationapi.entity.TranslationEntity;
import com.kood.homework.translationapi.model.ProjectLogger;
import com.kood.homework.translationapi.service.TranslationHistoryService;
import com.kood.homework.translationapi.util.IpRateLimiter;


@TestPropertySource(locations = "classpath:application-test.properties")
@WebMvcTest(TranslationHistoryController.class)
class TranslationHistoryControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IpRateLimiter ipRateLimiter;

    @MockBean
    private TranslationHistoryService translationHistoryService;

    @MockBean
    private ProjectLogger projectLogger;

    @Autowired
    private TranslationHistoryController translationHistoryController;

    @Value("${translation.api.history.ratelimit}")
    private int translationHistoryRateLimit;

    private static final String API_HISTORY_PATH = "/api/translate/history";
    private static final String IP_ADDRESS = "127.0.0.1";

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(translationHistoryController).build();
    }


    @Test
    void testGetTranslationHistory_Success() throws Exception {

        when(ipRateLimiter.tryAcquire(API_HISTORY_PATH, IP_ADDRESS, translationHistoryRateLimit)).thenReturn(true);

        List<TranslationEntity> translationHistoryList = new ArrayList<>();
        when(translationHistoryService.getTranslationHistory(any())).thenReturn(translationHistoryList);

        mockMvc.perform(get(API_HISTORY_PATH)
                .param("order", "asc") 
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.body.code").value(200))
                .andExpect(jsonPath("$.body.type").value("OK"))
                .andExpect(jsonPath("$.body.details").value("Successfully received translation history"));
    }


    @Test
    void testGetTranslationHistory_InvalidInput() throws Exception {

        when(ipRateLimiter.tryAcquire(API_HISTORY_PATH, IP_ADDRESS, translationHistoryRateLimit)).thenReturn(true);

        List<TranslationEntity> translationHistoryList = new ArrayList<>();
        when(translationHistoryService.getTranslationHistory(any())).thenReturn(translationHistoryList);


        mockMvc.perform(get(API_HISTORY_PATH)
                .param("order", "ascc")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error.code").value(400))
                .andExpect(jsonPath("$.error.type").value("Bad Request"))
                .andExpect(jsonPath("$.error.details").value("Invalid order parameter"));
    }
}