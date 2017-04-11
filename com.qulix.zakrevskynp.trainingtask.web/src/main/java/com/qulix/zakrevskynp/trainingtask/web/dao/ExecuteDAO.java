package com.qulix.zakrevskynp.trainingtask.web.dao;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Execute actions from @{{@link Executable}}
 * @author Q-NZA
 */
public class ExecuteDAO {
    public static Object execute(String message, Executable ex) {
        try (Connection connection = ConnectionFactory.getConnection()) {
            return ex.exec(connection);
        } catch (SQLException e) {
            throw new RuntimeException(message, e);
        }
    }
}
