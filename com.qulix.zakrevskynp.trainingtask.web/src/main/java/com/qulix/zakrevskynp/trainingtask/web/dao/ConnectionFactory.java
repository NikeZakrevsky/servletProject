package com.qulix.zakrevskynp.trainingtask.web.dao;


import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.qulix.zakrevskynp.trainingtask.web.LoggingFactory;

/**
 * Factory fot getting Connection object
 * @author Q-NZA
 */
class ConnectionFactory {
    private static Properties dbProperties;
    private static String url;
    private static final String USER = "user";
    private static final String PASSWORD = "password";
    private static final String DRIVER_CLASS = "driverClass";
    private static final String URL = "url";
    private static final String JDBC_PROPERTIES_PATH =
            "com.qulix.zakrevskynp.trainingtask.web/src/main/resources/jdbc.properties";
    private static final String DATABASE_CONNECTION_ERROR = "Ошибка подключения к базе данных";
    private static final Logger LOGGER = LoggingFactory.getLogger();
    private static final String READ_PROPERTIES_ERROR = "Файл настроек базы данных не найден";
    
    private ConnectionFactory() {
    }

    private static void readDatabaseProperty() {
        dbProperties = new Properties();
        try {
            dbProperties.load(new FileInputStream(JDBC_PROPERTIES_PATH));
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, READ_PROPERTIES_ERROR);
        }
    }

    static {
        readDatabaseProperty();
        try {
            Class.forName(dbProperties.getProperty(DRIVER_CLASS));
            url = dbProperties.getProperty(URL);
        }
        catch (Exception e) {
            LOGGER.log(Level.SEVERE, DATABASE_CONNECTION_ERROR);
        }
    }

    /**
     * Getting an exist connection
     * @return Connection object
     * @throws SQLException throws while getting connection to database
     */
    static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, dbProperties.getProperty(USER), dbProperties.getProperty(PASSWORD));
    }
}
