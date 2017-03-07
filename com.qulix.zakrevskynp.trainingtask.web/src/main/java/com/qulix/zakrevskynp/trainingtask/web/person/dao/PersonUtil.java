package com.qulix.zakrevskynp.trainingtask.web.person.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import com.qulix.zakrevskynp.trainingtask.web.person.Person;

/**
 * Utilities for person's dao layer
 * @author Q-NZA
 */
public class PersonUtil {
    private static final String ID = "id";
    private static final String FNAME = "fname";
    private static final String SNAME = "sname";
    private static final String LNAME = "lname";
    private static final String POSITION = "position";

    /**
     * Create object from ResultSet
     *
     * @param resultSet resultSet for converting to object
     * @return created person object
     * @throws SQLException
     */
    public Person resultSetAsObject(ResultSet resultSet) throws SQLException {
        return new Person(resultSet.getInt(ID), resultSet.getString(FNAME), resultSet.getString(SNAME),
                resultSet.getString(LNAME), resultSet.getString(POSITION));
    }

    /**
     * Set parameters to prepared statement
     * @param preparedStatement link of the prepared statement for setting parameters
     * @param parameters Person object
     * @throws SQLException
     */
    public void setPreparedStatement(PreparedStatement preparedStatement, Map<String, Object> parameters) throws SQLException {
        preparedStatement.setString(1, (String)parameters.get(FNAME));
        preparedStatement.setString(2, (String)parameters.get(SNAME));
        preparedStatement.setString(3, (String)parameters.get(LNAME));
        preparedStatement.setString(4, (String)parameters.get(POSITION));
    }
}
