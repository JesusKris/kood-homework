package com.kood.homework.translationapi.model;

import java.lang.System.Logger.Level;

/**
 * A generic logging interface for providing abstraction over logging
 * dependencies.
 * 
 * <p>
 * The log levels are represented by the {@link java.util.logging.Level} enum.
 * <p>
 * <strong>Author:</strong> JesusKris
 */
public interface ProjectLogger {

    /**
     * Logs a message at the specified log level.
     *
     * @param level   The log level indicating the severity of the message.
     * @param message The message to be logged.
     * 
     * @see java.util.logging.Level
     */
    void log(Level level, String message);

    /**
     * Logs a message at the specified log level with an associated throwable.
     *
     * @param level     The log level indicating the severity of the message.
     * 
     * @param message   The message to be logged.
     * @param throwable A throwable associated with the log message.
     * 
     * @see java.util.logging.Level
     */
    void log(Level level, String message, Throwable throwable);
}
