package com.kood.homework.translationapi.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.kood.homework.translationapi.TranslationapiApplication;
import com.kood.homework.translationapi.model.ProjectLogger;
import com.kood.homework.translationapi.util.LogBackLogger;


/**
 * Configuration class for creating the ProjectLogger bean.
 * 
 * 
 * <p>
 * <strong>Author:</strong> JesusKris
 * </p>
 */
@Configuration
public class ProjectLoggerConfig {

    @Bean
    ProjectLogger projectLogger() {
        LogBackLogger logger = new LogBackLogger(TranslationapiApplication.class);

        return logger;
    }
}