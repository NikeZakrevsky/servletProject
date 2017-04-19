package com.qulix.zakrevskynp.trainingtask.web.dao;

/**
 * Custom runtime exception
 * @author Q-NZA
 */
public class DAOException extends RuntimeException {
    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }

    public DAOException(Throwable cause) {
        super(cause);
    }
}
