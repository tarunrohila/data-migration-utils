package com.rohila.api.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Class which is used to initialize spring boot application
 *
 * @author Tarun Rohila
 * @version 1.0
 * @since April 15, 2023
 */
@SpringBootApplication
public class Application {

    /** Logger declaration */
    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        try {
            LOGGER.info("Initializing spring boot application");
            SpringApplication.run(Application.class, args);
        } catch (Exception e) {
            LOGGER.error("Failed to initialize spring boot application");
            throw new RuntimeException(e);
        }
    }
}
