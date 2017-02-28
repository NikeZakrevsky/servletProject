package com.qulix.trainingtask.dao;


import com.qulix.trainingtask.dao.exception.DAOException;
import com.qulix.trainingtask.dao.task.TasksDAOImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Get Connection object
 */
public class ConnectionFactory {
    private static final String driverUrl = "org.hsqldb.jdbcDriver";
    private static final String URL = "jdbc:hsqldb:hsql://localhost:9001/testdb";
    private static final String USER = "SA";
    private static final String PASSWORD = "";

    private static ConnectionFactory instance = new ConnectionFactory();

    private Logger logger = Logger.getLogger(TasksDAOImpl.class.getName());

    private ConnectionFactory() {
        try {
            Class.forName(driverUrl);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Create connection object
     *
     * @return
     * @throws DAOException
     */
    private Connection createConnection() throws DAOException {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage());
            throw new DAOException(e);
        }
    }

    /**
     * Get exist connection
     *
     * @return Connection object
     * @throws DAOException
     */
    public static Connection getConnection() throws DAOException {
        return instance.createConnection();
    }
}
