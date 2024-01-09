package com.kood.homework.translationapi.util;

import java.lang.System.Logger.Level;

import org.slf4j.LoggerFactory;


/**
 * A logger class which uses {@link org.slf4j.Logger} under the hood.
 * 
 * <p><strong>Author:</strong> JesusKris</p>
 */
public class LogBackLogger implements ProjectLogger {

    private final org.slf4j.Logger logger;

    public LogBackLogger(Class<?> clazz) {
        this.logger = LoggerFactory.getLogger(clazz);
    }
    
    @Override
    public void log(Level level, String message) {

        switch (level) {
            case TRACE:
                logger.trace(message);
                break;

            case DEBUG:
                logger.debug(message);
                break;

            case INFO:
                logger.info(message);
                break;

            case WARNING:
                logger.warn(message);
                break;

            case ERROR:
                logger.error(message);
                break;
        
            default:
                logger.warn("No explicit logger level set, please always set the logger level for accurate logging.");
                break;
        }
    }


    @Override
    public void log(Level level, String message, Throwable throwable) {
       switch (level) {
            case TRACE:
                logger.trace(message, throwable);
                break;

            case DEBUG:
                logger.debug(message, throwable);
                break;

            case INFO:
                logger.info(message, throwable);
                break;

            case WARNING:
                logger.warn(message, throwable);
                break;

            case ERROR:
                logger.error(message, throwable);
                break;
        
            default:
                logger.info(message, throwable);
                break;
        }
    }

}
