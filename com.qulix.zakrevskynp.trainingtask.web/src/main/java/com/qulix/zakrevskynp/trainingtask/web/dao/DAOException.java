package com.qulix.zakrevskynp.trainingtask.web.dao;

/**
 * Custom runtime exception
 * @author Q-NZA
 */
class DAOException extends RuntimeException {
    DAOException(String message, Throwable cause) {
        super(message, cause);
    }
}
