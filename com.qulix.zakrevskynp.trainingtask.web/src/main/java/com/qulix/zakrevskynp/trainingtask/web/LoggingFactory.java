package com.qulix.zakrevskynp.trainingtask.web;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * Logging setting
 * 
 * @author Q-NZA
 */
public class LoggingFactory {
    private static final Logger LOGGER = Logger.getLogger(LoggingFactory.class.getName());
    private static final String LOAD_CONFIGURATION_ERROR = "Error while loading logger configuration";

    static {
        try {
            FileInputStream configurationFile = new FileInputStream("com.qulix.zakrevskynp.trainingtask.web/src/main/resources/logger." +
                    "properties");
            LogManager.getLogManager().readConfiguration(configurationFile);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Exception: " + LOAD_CONFIGURATION_ERROR + e);
        }
    }

    /**
     * Gets the logger
     *
     * @return logger
     */
    public static Logger getLogger() {
        return LOGGER;
    }
}
