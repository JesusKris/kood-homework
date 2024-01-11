package com.kood.homework.translationapi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.deepl.api.Translator;

@Configuration
public class TranslatorConfig {

    @Value("${translation.api.deepl.key}")
    private String apiKey;

    @Bean
    Translator translator() {
        return new Translator(apiKey);
    }
}