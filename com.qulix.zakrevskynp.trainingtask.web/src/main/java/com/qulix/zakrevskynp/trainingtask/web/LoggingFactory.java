package com.qulix.zakrevskynp.trainingtask.web;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * Logging setting.
 * 
 * @author Q-NZA
 */
public class LoggingFactory {
    private static final Logger LOGGER = Logger.getLogger(LoggingFactory.class.getName());
    private static final String LOAD_CONFIGURATION_ERROR = "Error while loading logger configuration";
    private static final String LOGGER_PATH = "logger.properties";

    static {
        try {
            InputStream stream = LoggingFactory.class.getClassLoader().getResourceAsStream(LOGGER_PATH);
            LogManager.getLogManager().readConfiguration(stream);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, LOAD_CONFIGURATION_ERROR, e);
        }
    }

    /**
     * Gets the logger.
     *
     * @return logger.
     */
    public static Logger getLogger() {
        return LOGGER;
    }
}
