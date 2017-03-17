package com.qulix.zakrevskynp.trainingtask.web;


import java.sql.SQLException;

/**
 * Hide handling exception in dao classes
 * @author Q-NZA
 */
public interface Executable {
    Object exec() throws SQLException;
}
