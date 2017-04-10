package com.qulix.zakrevskynp.trainingtask.web;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by ZakrevskyNP on 10.04.2017.
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
