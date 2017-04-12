package com.qulix.zakrevskynp.trainingtask.web.dao;


import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.qulix.zakrevskynp.trainingtask.web.dao.task.TasksDAOImpl;

/**
 * Get Connection object
 * @author Q-NZA
 */
public class ConnectionFactory {
    private static Properties dbProperties;
    private static String url;

    private static Logger logger = Logger.getLogger(TasksDAOImpl.class.getName());

    private ConnectionFactory() {
    }

    static {
        try {
            dbProperties = new Properties();
            dbProperties.load(new FileInputStream("com.qulix.zakrevskynp.trainingtask.web/src/main/resources/jdbc.properties"));
            Class.forName(dbProperties.getProperty("driverClass"));
            url = dbProperties.getProperty("url");
        }
        catch (Exception e) {
            logger.log(Level.SEVERE, "Can not connect");
        }
    }

    /**
     * Get exist connection
     *
     * @return Connection object
     * @throws SQLException throws while getting connection to database
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, dbProperties.getProperty("user"), dbProperties.getProperty("password"));
    }
}