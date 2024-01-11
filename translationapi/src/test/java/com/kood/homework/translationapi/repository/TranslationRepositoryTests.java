package com.kood.homework.translationapi.repository;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import com.kood.homework.translationapi.entity.TranslationEntity;


@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class TranslationRepositoryTests {

    @Autowired
    private TranslationRepository translationRepository;

    @Test
    void saveAndFlushTranslation() {

        String inputText = "Hello";
        String translation = "Bonjour le monde";
        String sourceLang = "EN";
        String targetLang = "FR";

        TranslationEntity translationEntity = new TranslationEntity();
        translationEntity.setInputText(inputText);
        translationEntity.setTranslation(translation);
        translationEntity.setSourceLang(sourceLang);
        translationEntity.setTargetLang(targetLang);


        TranslationEntity savedTranslation = translationRepository.saveAndFlush(translationEntity);


        assertEquals(1, savedTranslation.getId());
        assertEquals(inputText, savedTranslation.getInputText());
        assertEquals(translation, savedTranslation.getTranslation());
        assertEquals(sourceLang, savedTranslation.getSourceLang());
        assertEquals(targetLang, savedTranslation.getTargetLang());
    }

    @Test
    void testFindAllByOrderByCreatedAtDesc() {

        TranslationEntity translationEntity1 = new TranslationEntity();
        translationEntity1.setInputText("Hello");
        translationEntity1.setTranslation("Bonjour");
        translationEntity1.setSourceLang("EN");
        translationEntity1.setTargetLang("FR");
        translationRepository.saveAndFlush(translationEntity1);

        TranslationEntity translationEntity2 = new TranslationEntity();
        translationEntity2.setInputText("Bonjour");
        translationEntity2.setTranslation("Hello");
        translationEntity2.setSourceLang("FR");
        translationEntity2.setTargetLang("EN");
        translationRepository.saveAndFlush(translationEntity2);


        List<TranslationEntity> translations = translationRepository.findAllByOrderByCreatedAtDesc();


        assertEquals(2, translations.size());
        assertTrue(translations.get(0).getCreatedAt().after(translations.get(1).getCreatedAt()));
    }

    @Test
    void testFindAllByOrderByCreatedAtAsc() {

        TranslationEntity translationEntity1 = new TranslationEntity();
        translationEntity1.setInputText("Hello");
        translationEntity1.setTranslation("Bonjour");
        translationEntity1.setSourceLang("EN");
        translationEntity1.setTargetLang("FR");
        translationRepository.saveAndFlush(translationEntity1);

        TranslationEntity translationEntity2 = new TranslationEntity();
        translationEntity2.setInputText("Bonjour");
        translationEntity2.setTranslation("Hello");
        translationEntity2.setSourceLang("FR");
        translationEntity2.setTargetLang("EN");
        translationRepository.saveAndFlush(translationEntity2);


        List<TranslationEntity> translations = translationRepository.findAllByOrderByCreatedAtAsc();


        assertEquals(2, translations.size());
        assertTrue(translations.get(0).getCreatedAt().before(translations.get(1).getCreatedAt()));
    }
}