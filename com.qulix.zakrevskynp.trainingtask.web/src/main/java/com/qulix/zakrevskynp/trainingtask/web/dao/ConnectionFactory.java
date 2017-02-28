package com.qulix.zakrevskynp.trainingtask.web.dao;


import com.qulix.zakrevskynp.trainingtask.web.dao.exception.DAOException;
import com.qulix.zakrevskynp.trainingtask.web.dao.task.TasksDAOImpl;

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
    }

    /**
     * Create connection object
     *
     * @return new Connection object
     * @throws DAOException
     */
    private Connection createConnection() throws DAOException, ClassNotFoundException {
        Class.forName(driverUrl);
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
    public static Connection getConnection() throws DAOException, ClassNotFoundException {
        return instance.createConnection();
    }
}
