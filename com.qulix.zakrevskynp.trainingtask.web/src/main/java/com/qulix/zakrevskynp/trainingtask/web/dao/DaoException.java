package com.qulix.zakrevskynp.trainingtask.web.dao;

/**
 * Custom runtime exception
 *
 * @author Q-NZA
 */
class DaoException extends RuntimeException {
    DaoException(String message, Throwable cause) {
        super(message, cause);
    }
}
