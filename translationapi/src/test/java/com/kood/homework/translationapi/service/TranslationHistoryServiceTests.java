package com.kood.homework.translationapi.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import com.kood.homework.translationapi.entity.TranslationEntity;
import com.kood.homework.translationapi.model.TranslationHistoryParameter;
import com.kood.homework.translationapi.repository.TranslationRepository;


@TestPropertySource(locations = "classpath:application-test.properties")
@SpringBootTest
class TranslationHistoryServiceTests {

    @Autowired
    private TranslationHistoryService translationHistoryService;

    @MockBean
    private TranslationRepository translationRepository;


    @Test
    void testSaveTranslation() throws Exception {
      
        String inputText = "Hello";
        String translation = "Bonjour";
        String sourceLang = "EN";
        String targetLang = "FR";

        translationHistoryService.saveTranslation(inputText, translation, sourceLang, targetLang);


        verify(translationRepository, times(1)).save(any(TranslationEntity.class));
    }


    @Test
    void testGetTranslationHistoryAsc() throws Exception {
        // Given
        TranslationHistoryParameter historyParameter = new TranslationHistoryParameter("asc");
        List<TranslationEntity> expectedResult = Arrays.asList(createTranslationEntity(), createTranslationEntity());

        when(translationRepository.findAllByOrderByCreatedAtAsc()).thenReturn(expectedResult);

        List<TranslationEntity> result = translationHistoryService.getTranslationHistory(historyParameter);

     
        verify(translationRepository, times(1)).findAllByOrderByCreatedAtAsc();
        assertEquals(expectedResult, result); 
    }


    @Test
    void testGetTranslationHistoryDesc() throws Exception {
        
        TranslationHistoryParameter historyParameter = new TranslationHistoryParameter("desc");
        List<TranslationEntity> expectedResult = Arrays.asList(createTranslationEntity(), createTranslationEntity());

        when(translationRepository.findAllByOrderByCreatedAtDesc()).thenReturn(expectedResult);

      
        List<TranslationEntity> result = translationHistoryService.getTranslationHistory(historyParameter);

      
        verify(translationRepository, times(1)).findAllByOrderByCreatedAtDesc(); // Verify repository method called
        assertEquals(expectedResult, result); // Verify the result
    }


    private TranslationEntity createTranslationEntity() {

        TranslationEntity translationEntity = new TranslationEntity();
        translationEntity.setInputText("Hello");
        translationEntity.setTranslation("Bonjour");
        translationEntity.setSourceLang("EN");
        translationEntity.setSourceLang("FR");

        return translationEntity;
    }
}