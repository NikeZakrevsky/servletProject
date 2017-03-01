package com.qulix.zakrevskynp.trainingtask.web.dao.exception;

import java.sql.SQLException;

/**
 * Custom exception for DAO layer
 * @author Q-NZA
 */
public class DAOException extends SQLException {

    public DAOException() {
        super();
    }
    public DAOException(String message) {
        super(message);
    }
    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }
    public DAOException(Throwable cause) {
        super(cause);
    }
}
