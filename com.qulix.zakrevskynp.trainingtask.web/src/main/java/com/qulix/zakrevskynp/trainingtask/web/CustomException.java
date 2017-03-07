package com.qulix.zakrevskynp.trainingtask.web;

import java.sql.SQLException;

/**
 * Custom exception for DAO layer
 * @author Q-NZA
 */
public class CustomException extends SQLException {

    public CustomException() {
        super();
    }
    public CustomException(String message) {
        super(message);
    }
    public CustomException(String message, Throwable cause) {
        super(message, cause);
    }
    public CustomException(Throwable cause) {
        super(cause);
    }
}
