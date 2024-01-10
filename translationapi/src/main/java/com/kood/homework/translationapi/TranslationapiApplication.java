package com.kood.homework.translationapi;

import java.lang.System.Logger.Level;
import java.net.InetAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.Environment;
import com.kood.homework.translationapi.model.ProjectLogger;
import io.micrometer.common.util.StringUtils;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class TranslationapiApplication implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private ProjectLogger logger;

    @Autowired
    private Environment environment;

    public static void main(String[] args) {
        SpringApplication.run(TranslationapiApplication.class, args);
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        checkDeeplApiKey();
        logger.log(Level.INFO, "Spring Boot Translation API running on "
                + InetAddress.getLoopbackAddress().getHostAddress() + ":" + environment.getProperty("server.port"));
    }

    private void checkDeeplApiKey() {
        if (StringUtils.isEmpty(environment.getProperty("deepl.api.key"))) {
            throw new RuntimeException("DeepL API key not set. Please set the 'deepl.api.key' environment variable.");
        }
    }
}
