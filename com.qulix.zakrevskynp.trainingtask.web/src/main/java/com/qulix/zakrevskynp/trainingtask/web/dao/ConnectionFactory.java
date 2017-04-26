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
    private static final String USER = "user";
    private static final String PASSWORD = "password";
    private static final String DRIVER_CLASS = "driverClass";
    private static final String URL = "url";
    private static final String JDBC_PROPERTIES = "com.qulix.zakrevskynp.trainingtask.web/src/main/resources/jdbc.properties";
    private static final String DATABASE_CONNECTION_ERROR = "Ошибка подключения к базе данных";
    private static final Logger LOGGER = LoggingFactory.getLogger();
    private static final String READ_PROPERTIES_ERROR = "Файл настроек базы данных не найден";

    private ConnectionFactory() {
    }

    static void readDatabaseProperty() {
        dbProperties = new Properties();
        try {
            dbProperties.load(new FileInputStream(JDBC_PROPERTIES));
        }
        catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Exception: " + READ_PROPERTIES_ERROR + e);
        }
    }

    {
        try {
            Class.forName(dbProperties.getProperty(DRIVER_CLASS));
        }
        catch (ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "Exception: " + DATABASE_CONNECTION_ERROR + e);
        }
    }

    /**
     * Getting an exist connection
     * @return Connection object
     * @throws SQLException throws while getting connection to database
     */
    static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dbProperties.getProperty(URL), dbProperties.getProperty(USER), dbProperties.getProperty(PASSWORD));
    }
}
