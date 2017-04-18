package com.qulix.zakrevskynp.trainingtask.web;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class LoggingFactory {
    private static Properties preferences = new Properties();
    private static final Logger logger = Logger.getLogger(LoggingFactory.class.getName());

    static {
        try {
            FileInputStream configFile = new FileInputStream("logger.properties");
            preferences.load(configFile);
            LogManager.getLogManager().readConfiguration(configFile);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error while loading logger configuration");
        }
    }

    public static Logger getLogger() {
        return logger;
    }
}