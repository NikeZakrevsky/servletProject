package com.qulix.zakrevskynp.trainingtask.web;


import java.sql.Connection;
import java.sql.SQLException;

/**
 * Handling exception in dao classes
 * @author Q-NZA
 */
public interface Executable {
    Object exec(Connection connection) throws SQLException;
}
