package com.qulix.zakrevskynp.trainingtask.web.dao;


import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.qulix.zakrevskynp.trainingtask.web.LoggingFactory;

/**
 * Factory fot getting Connection object.
 *
 * @author Q-NZA
 */
class ConnectionFactory {
    private static final String USER = "user";
    private static final String PASSWORD = "password";
    private static final String DRIVER_CLASS = "driverClass";
    private static final String URL = "url";
    private static final String JDBC_PROPERTIES = "jdbc.properties";
    private static final String DATABASE_CONNECTION_ERROR = "Ошибка подключения к базе данных";
    private static final Logger LOGGER = LoggingFactory.getLogger();
    private static final String READ_PROPERTIES_ERROR = "Файл настроек базы данных не найден";
    private static String urlProperty;
    private static String userProperty;
    private static String passwordProperty;
    private static String driverProperty;

    private ConnectionFactory() {
    }

    static  {
        readDatabaseProperties();
        loadDriver();
    }

    /**
     * Obtains an existing connection.
     *
     * @return Connection object.
     * @throws SQLException throws while getting connection to database.
     */
    static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(urlProperty, userProperty, passwordProperty);
    }
    
    /**
     * Reads properties of the database connection.
     */
    private static void readDatabaseProperties() {
        InputStream stream = ConnectionFactory.class.getClassLoader().getResourceAsStream(JDBC_PROPERTIES);
        Properties databaseProperties = new Properties();
        try {
            databaseProperties.load(stream);
            urlProperty = databaseProperties.getProperty(URL);
            userProperty = databaseProperties.getProperty(USER);
            passwordProperty = databaseProperties.getProperty(PASSWORD);
            driverProperty = databaseProperties.getProperty(DRIVER_CLASS);
        }
        catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Exception: " + READ_PROPERTIES_ERROR + e);
        }
    }

    /**
     * Loads the database driver.
     */
    private static void loadDriver() {
        try {
            Class.forName(driverProperty);
        }
        catch (ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "Exception: " + DATABASE_CONNECTION_ERROR + e);
        }
    }
}
