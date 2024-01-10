package com.kood.homework.translationapi;


import java.lang.System.Logger.Level;
import java.net.InetAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import com.kood.homework.translationapi.model.ProjectLogger;
import io.micrometer.common.util.StringUtils;


@SpringBootApplication
public class TranslationapiApplication implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private ProjectLogger logger;

    @Value("${translation.api.deepl.key}")
    private String deeplApiKey;

    @Value("${translation.api.server.port}")
    private String serverPort;


    public static void main(String[] args) {
        SpringApplication.run(TranslationapiApplication.class, args);
    }


    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        validateDeeplApiKeyPresence();
        logger.log(Level.INFO, "Spring Boot Translation API running on "
                + InetAddress.getLoopbackAddress().getHostAddress() + ":" + serverPort);
    }


    private void validateDeeplApiKeyPresence() {
        if (StringUtils.isEmpty(deeplApiKey)) {
            throw new RuntimeException("DeepL API key not set. Please set the 'deepl.api.key' environment variable.");
        }
    }
}