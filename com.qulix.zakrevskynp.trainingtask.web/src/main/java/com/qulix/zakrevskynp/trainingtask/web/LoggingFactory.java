package com.qulix.zakrevskynp.trainingtask.web;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * Logging setting
 * @author Q-NZA
 */
public class LoggingFactory {
    private static final Logger LOGGER = Logger.getLogger(LoggingFactory.class.getName());

    static {
        try {
            FileInputStream configFile = new FileInputStream("logger.properties");
            LogManager.getLogManager().readConfiguration(configFile);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error while loading logger configuration");
        }
    }

    public static Logger getLogger() {
        return LOGGER;
    }
}
