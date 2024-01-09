package com.kood.homework.translationapi;

import java.lang.System.Logger.Level;
import java.net.InetAddress;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.core.env.Environment;

import com.kood.homework.translationapi.util.ProjectLogger;


@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class TranslationapiApplication implements CommandLineRunner {

	private ProjectLogger logger;

	@Autowired
    private Environment environment;

    public TranslationapiApplication(ProjectLogger logger) {
        this.logger = logger;
    }

	public static void main(String[] args) {
		SpringApplication.run(TranslationapiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// Use the service
		logger.log(Level.INFO, "Spring Boot Translation API running on " + InetAddress.getLoopbackAddress().getHostAddress() + ":" + environment.getProperty("server.port"));
	}

}
